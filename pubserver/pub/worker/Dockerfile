FROM ubuntu:16.04

MAINTAINER zihua <yuanzihua@qiniu.com>
ENV wdir /usr/src/app
ENV LD_LIBRARY_PATH=${wdir}/bin/lib
WORKDIR ${wdir}
RUN mkdir -p ${wdir}/logs/
COPY workerd workerd.conf ./
COPY bin ./bin                         
CMD ["./workerd","workerd.conf"]

