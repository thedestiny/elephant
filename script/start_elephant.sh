#!/bin/bash

TIMESTAMP=`date +%Y%m%d-%H%M`
PROJECT_PATH=`cd \`dirname $0\`;pwd`

## step 1 package
cd $PROJECT_PATH

## # nohup java -jar platform-1.0.jar --spring.profiles.active=prod --server.port  > nohup.out &

echo " $TIMESTAMP start elephant ^_^ ..." >>  $PROJECT_PATH/logs

sleep 10
echo " this is start !!!!!!!!!!! ELEPHENT !!!!!!!!!!!!"
echo " $TIMESTAMP start elephant ^_^ ..." >>  $PROJECT_PATH/logs
cat /etc/hosts

cd /root/app

java -jar elephant-1.0.jar -Djava.security.egd=file:/dev/./urandom --spring.profiles.active=docker

sleep 1
touch nohup.out
tail -f nohup.out

