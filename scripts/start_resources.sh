#!/usr/bin/env bash

# this is script can be used to run locally the application

# if you want to run the unit test you don't have to run this script.

# In order to run it, go into local folder and run the following script /start_resources.sh

set -e # when we get an error, this command avoid to run commands unnecessarily and it stop it afterwards.

ROOT_DIR=$(cd .. && pwd)

cd $ROOT_DIR

devops/scripts/before_tests.sh

cd local

ID=$(docker ps -a | grep cassandra | awk '{print $1}')

#here there is the necessity to clean the docker removing cassandra containers
if [[ ! -z $ID ]] ; then
    docker stop $ID
    docker rm $ID
fi

ROOT_DIR=$(cd .. && pwd)

sudo docker-compose up -d
DCK_CASSANDRA=$(sudo docker-compose ps | grep cassandra1 | awk '{print $1}')

echo ""

printf "Waiting for Cassandra"

while [[ $(sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -e \"DESC KEYSPACES;\"" | grep -c system) -eq 0 ]]
do
    printf '.'
    sleep 3
done

echo ""

#sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -e \"DROP KEYSPACE orders;\""
sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -e \"DESC KEYSPACES;\""
sudo docker exec -it $DCK_CASSANDRA sh -c "rm -rf /tmp/*"

if [[ $(sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -e \"DESC KEYSPACES;\"" | grep orders -c) -eq 0 ]] ; then
	sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -e \"CREATE KEYSPACE orders  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };\""
	cd $ROOT_DIR/db/migrations/
	for file in *.cql
	do
		echo " --- migrate ${file} --- "
		sudo docker cp $file $DCK_CASSANDRA:/tmp/${file}
		sudo docker exec -it $DCK_CASSANDRA sh -c "cqlsh -k orders -f '/tmp/${file}'"
	done
	sudo docker exec -it $DCK_CASSANDRA sh -c "rm -rf /tmp/*"
fi