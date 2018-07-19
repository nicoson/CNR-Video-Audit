# -*- coding: utf-8 -*-
# flake8: noqa
from qiniu import Auth, QiniuMacAuth, http, put_file, etag, urlsafe_base64_encode
import time
import requests
import json


class service(object):
    def __init__(self):
        # self.access_key = 'M-G8vwdVdmKYKk50ZdCcIyizX1ItahHnJN-lWsSG'
        # self.secret_key = 'onBC_RiBMOa6cTvUDmpgpguDNZRz4Q_5oW5bkYlA'
        # self.bucket_name = 'customer-demo-bjrun-nxwa'
        # self.key = 'duchayuan/'
        # self.uri = 'http://p7fftezb2.bkt.clouddn.com/'
        self.access_key = 'gzkI_VI9XfvvM2K0HUxU3YgC-J2a1dRDUReJBDzX'
        self.secret_key = 'YjtJq7ovo7wYaIbpNxlye0EX3q8qjynHBcTi59gk'
        self.bucket_name = 'video-review'
        self.key = 'duchayuan/'
        self.uri = 'http://pbvz06277.bkt.clouddn.com/'

    def uploadbucket(self,filename):
        q = Auth(self.access_key, self.secret_key)

        key = self.key + str(int(time.time()*1000)) + '-' + filename
        token = q.upload_token(self.bucket_name, key, 3600)

        localfile = './files/' + filename
        ret, info = put_file(token, key, localfile)
        print(info)
        print(ret)
        return ret

    # def createfacegroup(self, facegroup):
    #     address = "http://argus.atlab.ai/v1/face/group/new"
    #     data = map(lambda x: {"uri": self.uri + x.uri, "attribute": {"id":x.id, "name":x.id}}, facegroup)
    #     authorization = Auth(self.access_key, self.secret_key)
    #     result = requests.post(address, data=data, auth=authorization)
    #     return result

    def createfacegroup(self, groupid, facegroup):
        """
        returns a dict that contains the following information
        """
        req_host = "argus.atlab.ai"
        req_api = "/v1/face/group/" + groupid + "/new"
        req_url = "http://" + req_host + req_api
        data = list(map(lambda x: {"uri": self.uri + x["uri"], "attribute": {"id":x["id"], "name":x["id"]}}, facegroup))
        print(data)
        authorization = QiniuMacAuth(self.access_key, self.secret_key)

        token = authorization.token_of_request(
            method = 'POST',
            host = req_host,
            url = req_api,
            content_type='application/json',
            qheaders='',
            body=json.dumps({"data":data})
        )
        token = 'Qiniu ' + token
        headers = {"Content-Type": "application/json", "Authorization": token}

        res = requests.post(req_url, headers=headers, data=json.dumps({"data":data}))
        print('cloud ajax call result: ')
        print(res)

        # res = {'faces': ['3', '1', '2']}

        return json.loads(res.content.decode('utf8'))

    def checkfacegroup(self, groupid):
        req_host = "argus.atlab.ai"
        req_api = "/v1/face/group/" + groupid
        req_url = "http://" + req_host + req_api
        authorization = QiniuMacAuth(self.access_key, self.secret_key)

        token = authorization.token_of_request(
            method='GET',
            host = req_host,
            url = req_api,
            content_type='application/json',
            qheaders=''
        )
        token = 'Qiniu ' + token
        headers = {"Content-Type": "application/json", "Authorization": token}

        res = requests.get(req_url, headers=headers)
        print(res.content)

        return json.loads(res.content.decode('utf8'))

    
    def videocheck(self, groupid, videoid, videouri):
        req_host = "argus.atlab.ai"
        req_api = "/v1/video/" + videoid
        req_url = "http://" + req_host + req_api
        authorization = QiniuMacAuth(self.access_key, self.secret_key)
        data = {
            "data":{
                "uri": videouri
            },
            "params": {
                "vframe": {
                    "mode": 0,
                    "interval": 0.5
                }
            },
            "ops": [
                {
                    "op": "face_group_search",
                    "params": {
                        "other": {
                            "group": groupid
                        }
                    }
                }
            ]
        }
        print(json.dumps(data))

        token = authorization.token_of_request(
            method='POST',
            host = req_host,
            url = req_api,
            content_type='application/json',
            qheaders='',
            body = json.dumps(data)
        )
        token = 'Qiniu ' + token
        headers = {"Content-Type": "application/json", "Authorization": token}

        res = requests.post(req_url, headers=headers, data=json.dumps(data))
        print(res.content)

        return json.loads(res.content.decode('utf8'))


    def pulpdetect(self, uri):
        req_host = "serve.atlab.ai"
        req_api = "/v1/eval/pulp-detect"
        req_url = "http://" + req_host + req_api
        print(req_url)
        authorization = QiniuMacAuth('yvkBAhylLK6HTrhU644UcFiVeFhRMR4geKGB1Prt', '1Kfm9tUJURJWxYFHWL1X-HuVVFMMEPwn2S4j5EoW')
        data = {
            "data":{
                "uri": uri
            }
        }
        print(json.dumps(data))

        token = authorization.token_of_request(
            method='POST',
            host = req_host,
            url = req_api,
            content_type='application/json',
            qheaders='',
            body = json.dumps(data)
        )
        token = 'Qiniu ' + token
        headers = {"Content-Type": "application/json", "Authorization": token}

        res = requests.post(req_url, headers=headers, data=json.dumps(data))
        print(res)
        print(res.content)

        return json.loads(res.content.decode('utf8'))


    def politiciancheck(self, groupid, videoid, videouri):
        req_host = "argus.atlab.ai"
        req_api = "/v1/video/" + videoid
        req_url = "http://" + req_host + req_api
        authorization = QiniuMacAuth(self.access_key, self.secret_key)
        data = {
            "data":{
                "uri": videouri
            },
            "params": {
                "async": False,
                "vframe": {
                    "mode": 0,
                    "interval": 1
                }
            },
            "ops": [
                {
                    # "hookURL": "http://100.100.56.220:8000/callback",
                    "op": "pulp"
                }
            ]
        }
        print(json.dumps(data))

        token = authorization.token_of_request(
            method='POST',
            host = req_host,
            url = req_api,
            content_type='application/json',
            qheaders='',
            body = json.dumps(data)
        )
        token = 'Qiniu ' + token
        headers = {"Content-Type": "application/json", "Authorization": token}

        res = requests.post(req_url, headers=headers, data=json.dumps(data))
        print(res.content)

        return json.loads(res.content.decode('utf8'))

