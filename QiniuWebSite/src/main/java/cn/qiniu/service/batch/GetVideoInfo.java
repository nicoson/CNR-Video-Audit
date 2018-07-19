//package cn.qiniu.service.batch;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.websocket.EncodeException;
//import javax.websocket.Session;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import ch.qos.logback.core.db.dialect.DBUtil;
//import cn.qiniu.service.video.VideoListService;
//
//
//public class GetVideoInfo {
//	@Autowired
//	private VideoListService videoListService;
//	private Logger logger = LoggerFactory.getLogger(GetVideoInfo.class);
//	
//	private int count = 1000;
//	private Session session;
//    private List currentMessage;
//    private DBUtil dbUtil;
//    private int currentIndex;
//	 
//	
//	public GetVideoInfo(Session session) {
//        this.session = session;
//        currentMessage = new ArrayList();
//        dbUtil = new DBUtil();
//        currentIndex = 0;//此时是0条消息
//    }
//	
////	@Transactional(readOnly=false)
////	public void execute() throws Exception {
////		logger.info("{0}",new Date());
////		
////		List<Integer> levelList = new ArrayList<>();
////		levelList.add(1);
////		levelList.add(2);
////		levelList.add(3);
////		//页数：1		 最大条数：1000 	 审核阶段：1(初步审核(机审结果))  危险等级：1,2,3(低、中、高)
////		String request = qiNiuService.getVideoList(1, count, 1, levelList);
////		
////	}
//	
//	@Transactional(readOnly=false)
//	 public void execute() throws Exception {
//		 logger.info("{0}",new Date());
////	        while (true) {
//		 		// TODO ch注释,
////	        	VideoCountByLevel count = videoListService.getCountByDangerLevel();
////	                    list = dbUtil.getFromDB();
//	            try {
//					session.getBasicRemote().sendObject(count);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (EncodeException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
////	            if (list != null && currentIndex < list.size()) {
////	                for (int i = currentIndex; i < list.size(); i++) {
////	                    try {
////	                        session.getBasicRemote().sendText(list.get(i).getFanNo()
////	                                + "," + list.get(i).getTime()
////	                                + "," + list.get(i).getDescription());
//////	                            session.getBasicRemote().sendObject(list.get(i)); //No encoder specified for object of class [class AlarmMessage]
////	                    } catch (IOException e) {
////	                        e.printStackTrace();
////	                    }
////	                }
////	                currentIndex = list.size();
////	            }
////	            try {
////	                //一秒刷新一次
////	                Thread.sleep(1000);
////	            } catch (InterruptedException e) {
////	                e.printStackTrace();
////	            }
//
////	        }
//	    }
//	 
//}
