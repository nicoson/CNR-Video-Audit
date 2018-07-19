FROM mysql:5.7

COPY ./qiniu ./qiniu
COPY ./import.sh ./
ENV MYSQL_ROOT_PASSWORD=Asd123321@@@
EXPOSE 3306

# ENTRYPOINT ["./import.sh"]