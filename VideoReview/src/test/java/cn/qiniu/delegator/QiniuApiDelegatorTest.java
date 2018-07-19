package cn.qiniu.delegator;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;

import cn.qiniu.entity.videoReviewToQiniu.QiniuReview;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewData;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewOp;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewParams;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewSegment;
import cn.qiniu.entity.videoReviewToQiniu.QiniuReviewVframe;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml"})
public class QiniuApiDelegatorTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	QiniuApiDelegator delegator;
	
	@Test
	public void testSetWatermark(){
//		http://pbvz06277.bkt.clouddn.com/o_1cilnb0da1tsv14lh1dfp1mou15s2j.mp4
//		http://pbvz06277.bkt.clouddn.com/qweqweqwe.mp4
		String key = "o_1cilnb0da1tsv14lh1dfp1mou15s2j.mp4";
		String newKey = "qweqweqwe.mp4";
		delegator.setWatermark(key, newKey);
	}
	
//	@Test
	public void getVideoAvinfo(){
		String a = delegator.getVideoAvinfo("http://ow8zztdmn.bkt.clouddn.com/o_1bt2cl6pfsubveddbp1l961lcr9.mp4");
		System.out.println(a);
		JSONObject b = JSONObject.parseObject(a);
		List<Object> list = b.getJSONArray("streams");
		Object aaa = JSONObject.toJSON(list.get(0));
		b = JSONObject.parseObject(aaa.toString());
		String result = b.getString("duration");
		
		
//		Object c = b.getJSONObject("streams");
//		byte[] c = b.getBytes("streams");
//		Object d = c.get("duration");
//		System.out.println(d);
		System.out.println(result);
		
	}
	
//	@Test
	public void testCutVideo(){
		String key = "testforwatermark.mp4";
		delegator.cutVideo("1507737988307.mp4","1507737988307_R.mp4" ,"0", "2");
	}
//	
//	@Test
	public void testchangeVideoSize(){
		String key = "testforwatermark.mp4";
		delegator.changeVideoSize("movie.mp4");
	}
	
//	@Test
	public void testgetVideoPic(){
		delegator.getVideoPic("o_1btbhbj4b48u1q361mcd1fkhh4ij.mp4", "o_1btbhbj4b48u1q361mcd1fkhh4ij_R.jpg", "");
	}
	
	@Test
	public void testSubmitVideo(){
		String a = "{\"op\":\"pulp\",\"id\":\"chtest70\"}";
		try {
		JSONObject.parseObject(a);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		String key = "testforwatermark.mp4";
		String newKey = "testaddwatermark.mp4";
		QiniuReview record = new QiniuReview();
		QiniuReviewData data = new QiniuReviewData();
		data.setUri("http://ow8zztdmn.bkt.clouddn.com/test22222");
		record.setData(data);
		QiniuReviewParams params = new QiniuReviewParams();
		params.setAsync(false);
		QiniuReviewSegment segment = new QiniuReviewSegment();
		segment.setMode(0);
		segment.setInterval(5);
		params.setSegment(segment);
		QiniuReviewVframe vframe = new QiniuReviewVframe();
		vframe.setMode(0);
		vframe.setInterval(5);
		params.setVframe(vframe);
		record.setParams(params);
		List<QiniuReviewOp> ops = new ArrayList<QiniuReviewOp>();
		QiniuReviewOp reviewOp = new QiniuReviewOp();
		reviewOp.setOp("pulp");
//		reviewOp.setHookURI("");
		ops.add(reviewOp);
		record.setOps(ops);
		String videoId = "1111";
//		delegator.submitVideo(record, videoId);
	}
}
