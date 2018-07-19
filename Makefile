all: clean dist

dist:
	mkdir -p target
	cd QiniuData && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cd QiniuWebSite && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cd VideoManager && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cd VideoManagerService && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cd VideoReview && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cd VideoReviewService && mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true && cd ..;
	cp QiniuData/target/QiniuData.war target
	cp QiniuWebSite/target/QiniuWebSite.war target
	cp VideoManager/target/VideoManager.war target
	cp VideoManagerService/target/VideoManagerService.war target
	cp VideoReview/target/VideoReview.war target
	cp VideoReviewService/target/VideoReviewService.war target

clean:
	rm -rf target
	rm -rf QiniuData/target;
	rm -rf QiniuWebSite/target;
	rm -rf VideoManager/target;
	rm -rf VideoManagerService/target;
	rm -rf VideoReview/target;
	rm -rf VideoReviewService/target;

deploy:
	ssh root@ai "rm -rf /root/target/*.war"
	scp -r target/*.war root@ai:/root/target
	ssh root@ai "export JAVA_HOME=/opt/jdk1.8 && export JRE_HOME=/opt/jdk1.8/jre && bash /opt/tomcat8/bin/shutdown.sh && \
	rm -r /opt/tomcat8/webapps/Qin* /opt/tomcat8/webapps/Video* && \
	cp /root/target/*.war /opt/tomcat8/webapps && \
	bash /opt/tomcat8/bin/startup.sh"


prepare:
	mvn install:install-file -Dpackaging=pom -DgroupId=cn.qiniu -DartifactId=base-pom -Dversion=1.0.0.RELEASE -Dfile=./cn/qiniu/base-pom/1.0.0.RELEASE/base-pom-1.0.0.RELEASE.pom
	mvn install:install-file -Dpackaging=jar -DgroupId=cn.qiniu -DartifactId=framework -Dversion=1.0 -Dfile=./cn/qiniu/framework/1.0/framework-1.0.jar

build:
	docker build -t video-v2.1 .

pushimg:
	docker tag video-v2.1 reg.qiniu.com/avatest/video-video:v9
	docker push reg.qiniu.com/avatest/video-video:v9

.PHONY: all
