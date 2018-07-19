FROM ubuntu:16.04

MAINTAINER zihua <yuanzihua@qiniu.com>
ENV wdir /usr/src/app
WORKDIR ${wdir}
RUN mkdir -p ${wdir}/logs/
COPY dispatcherd dispatcherd.conf ./
EXPOSE 9001

CMD ["./dispatcherd","dispatcherd.conf"]
