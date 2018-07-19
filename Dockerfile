FROM tomcat:server

MAINTAINER zihua <yuanzihua@qiniu.com>

COPY ./target/QiniuData.war /usr/local/tomcat/webapps
COPY ./target/QiniuWebSite.war /usr/local/tomcat/webapps
COPY ./target/VideoManager.war /usr/local/tomcat/webapps
COPY ./target/VideoManagerService.war /usr/local/tomcat/webapps
COPY ./target/VideoReview.war /usr/local/tomcat/webapps
COPY ./target/VideoReviewService.war /usr/local/tomcat/webapps
COPY ./server /usr/local/tomcat/server
EXPOSE 8080

CMD ["catalina.sh", "run"]
