#!/bin/bash

TIMESTAMP=`date +%Y%m%d-%H%M`
PROJECT_PATH=`cd \`dirname $0\`;cd ..;pwd`

## step 1 package
cd $PROJECT_PATH

if [ -d "$PROJECT_PATH/workspace" ];then
  rm -rf $PROJECT_PATH/workspace
  echo "文件夹已经存在"
fi

mkdir -p $PROJECT_PATH/workspace/{app,nginx,redis,mysql}

## 创建工作目录
mvn -U clean package -Dmaven.test.skip=true

##
cp target/elephant-1.0.jar  workspace/app/elephant-1.0.jar
cp script/start_elephant.sh workspace/app/start_elephant.sh
chmod +x workspace/app/start_elephant.sh




