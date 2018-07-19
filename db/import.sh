#!/bin/sh

HOSTNAME="127.0.0.1"                      #数据库信息
PORT="3306"
USERNAME="root"
PASSWORD="Asd123321@@@"
DBNAME="qiniu"                          #数据库名称

#创建数据库
create_db_sql="create database IF NOT EXISTS ${DBNAME}"
mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} -e "${create_db_sql}"

#导入表
dir=./qiniu
for file in ${dir}/*; do
    import_table_sql="source ${file}"
    mysql -h${HOSTNAME} -P${PORT} -u${USERNAME} -p${PASSWORD} -D${DBNAME} -e "${import_table_sql}"
done
