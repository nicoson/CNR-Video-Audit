# coding:utf-8
from flask import Flask, render_template
from flask import request
from flask_cors import CORS
import requests
import json
import time
import os
import commands
from model import service
from urlparse import urlparse
import base64
from qiniu import QiniuMacAuth
from threading import Thread

sv = service()
net = ''
interval = 2

UPLOAD_FOLDER = os.getcwd() + '/files'
GROUPID = 'G001'
# GROUPID = '123'
VIDEOID = 'V001'

# app = Flask(__name__)
app = Flask(__name__, static_folder='', static_url_path='')
CORS(app)

@app.route('/')
def hello_world():
    return render_template('index.html')

@app.route('/callback', methods=['POST'])
def callback():
    print('data: ', request.data)
    return 'done'

@app.route('/list')
def getlist():
    print('============>   get list ...')
    return json.dumps([{
        'title': 'test.mp4',
        'tag': [['sq'], ['sq', 'bl'], ['sq', 'bl', 'zz']],
        'cover': 'https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3543338685,3251613788&fm=85&s=160EF405DEC9832C6C51BEF803001037'
    }])


@app.route('/v1/video', methods=['POST'])
def video():
    data = json.loads(request.data.split('&&&')[0].encode('utf-8'))
    id = json.loads(request.data.split('&&&')[1].encode('utf-8'))['job']
    cburl = data['ops'][0]['hookURL']
    print 'cburl', cburl

    core(data,id,cburl)
    return json.dumps({'id':id})


    # return json.dumps(res)


@app.route('/pulpp', methods=['GET'])
def pulpp():
    imgfile = open('./img/o_1cifb9u4h6op1b1ul531par11c09/001.jpg','rb')
    img64 = 'data:application/octet-stream;base64,' + base64.b64encode(imgfile.read())
    res = pulp(img64)
    return json.dumps(res)


def pulp(url):
    access_key = 'yvkBAhylLK6HTrhU644UcFiVeFhRMR4geKGB1Prt'
    secret_key = '1Kfm9tUJURJWxYFHWL1X-HuVVFMMEPwn2S4j5EoW'
    req_url = 'http://argus.atlab.ai/v1/beta/pulp'
    postBody = {'data': {"uri": url}}
    token = QiniuMacAuth(access_key, secret_key).token_of_request(
        method='POST',
        host='argus.atlab.ai',
        url="/v1/beta/pulp",
        content_type='application/json',
        qheaders='',
        body=json.dumps(postBody)
    )
    token = 'Qiniu ' + token
    headers = {"Content-Type": "application/json", "Authorization": token}

    try:
        response = requests.post(req_url, headers=headers, data=json.dumps(postBody))
        res = json.loads(response.content)
        return res['result']
    except Exception, e:
        print 'search face group error:', e
        return {'error': e}

def async(f):
    def wrapper(*args, **kwargs):
        thr = Thread(target = f, args = args, kwargs = kwargs)
        thr.start()
    return wrapper

@async
def core(data,id,cburl):
    # download and cut video
    print('start downloading images ...')
    filename = urlparse(data['data']['uri']).path.replace('/','')
    savedir = 'img/' + filename[:-4]
    commands.getoutput('wget -P img/ ' + data['data']['uri'])
    commands.getoutput('mkdir ' + savedir)
    print('ffmpeg -i img/' + filename + ' -r ' + str(1./interval) + ' -f image2 ' + savedir + '/%3d.jpg')
    a = commands.getoutput('ffmpeg -i img/' + filename + ' -r ' + str(1./interval) + ' -f image2 ' + savedir + '/%3d.jpg')
    print a
    print('ffmpeg done !')
    

    # start request
    files = os.listdir(savedir)
    files.sort()
    segments = []
    cuts = []
    glabel = {}
    currentlabel = ''
    for ind in range(len(files)):
        if files[ind] == '.DS_Store':
            continue
        imgfile = open(savedir + '/' + files[ind],'rb')
        img64 = 'data:application/octet-stream;base64,' + base64.b64encode(imgfile.read())
        res = pulp(img64)
        print ind, files[ind], res
            

        if res['label'] != currentlabel:
            segments.append({
                "offset_begin": max(0,ind-1)*interval*1000,
                "offset_end": ind*interval*1000,
                "labels":[
                    {
                        "label": res['label'],
                        "score": res['score']
                    }
                ],
                "cuts": []
            })
            currentlabel = res['label']
        else:
            segments[-1]["offset_end"] = ind*interval*1000

        segments[-1]['cuts'].append({
            "offset": ind*interval*1000,
            "result":{
                "label": res['label'],
                "score": res['score'],
                "review": res['review']
            }
        })

        # update local score
        if res['score'] > segments[-1]['labels'][0]['score']:
            segments[-1]['labels'][0]['score'] = res['score']

        # update global score
        if res['label'] not in glabel or res['score'] > glabel[res['label']]:
            glabel[res['label']] = res['score']

    labels = []
    for label in glabel:
        labels.append({
            "label": label,
            "score": glabel[label]
        })

    res = {
        "op": "pulp_beta",
        "id": id,
        "code": 0,
        "message": "",
        "result": {
            "labels": labels,
            "segments": segments
        }
    }

    print(json.dumps(res))
    r = requests.post(cburl, headers = {"Content-Type": "application/json"}, data=json.dumps(res))
    print 'post done: ', r


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8888, debug=False)
