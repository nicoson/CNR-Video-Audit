FROM ubuntu


MAINTAINER yuanzihua@qiniu.com

WORKDIR /usr/src/app

COPY mgmtd.conf mgmtd ./
COPY static ./static
COPY views ./views

EXPOSE 9000

CMD ["./mgmtd","./mgmtd.conf"]
