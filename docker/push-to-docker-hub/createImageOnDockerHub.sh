#!/bin/bash

./gradlew build docker

ID=$(docker ps -a | grep heldersfaria/hiring:latest | awk '{print $1}')

sudo docker tag $ID heldersfaria/hiring:latest

sudo docker docker login

sudo docker push heldersfaria/hiring:latest


# tentei porem sem sucesso colocar o a aplicação e o banco de dados trocando informações.

# Esse foi um dos arquivos que segui.