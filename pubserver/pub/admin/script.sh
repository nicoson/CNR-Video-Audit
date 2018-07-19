#!/bin/sh
./admin set -db_url mongodb -db_name pub -log log_collection -user userconf

./admin reg admin admin
./admin conf -user admin -role admin
./admin conf -user admin -dispatcher add http://dispatcher:9001/api/action docker -limit 100000
