#!/usr/bin/env bash

set -e

ID=$(docker ps -a | grep mongo | awk '{print $1}')

if [[ ! -z $ID ]] ; then
    docker stop $ID
    docker rm $ID
fi

sudo docker-compose up -d