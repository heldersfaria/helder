#!/bin/bash

echo "Criando 2 MongoDb 1 para aplicação local e outro tests unitarios"

cd docker

./start-mongodb.sh

cd -

./gradlew clean build

java -Xmx512m -Xss1024k -jar build/libs/*.jar
