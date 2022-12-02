#!/bin/bash
git checkout  remotes/origin/develop
git fetch --all
git reset --hard origin/develop
git pull origin develop
mvn clean install -Dmaven.test.skip=true
rm -f ./docker/app.jar
mv ./target/push-test-0.0.1-SNAPSHOT.jar ./docker/app.jar
cd ./docker
ls -a
sudo chmod 777 deploy-app.sh
./deploy-app.sh