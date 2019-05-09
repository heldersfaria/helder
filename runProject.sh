#!/bin/bash

./gradlew build -x test

cd docker

./start-mongodb.sh

cd -

java -Xmx512m -Xss1024k -jar build/libs/*.jar
