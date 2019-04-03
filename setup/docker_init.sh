#!/bin/bash

DOCKER_CONTAINER="swingy-mysql"

docker-machine start Char
eval "$(docker-machine env Char)"

if [ "$(docker ps -a | grep $DOCKER_CONTAINER)" ]
then
	docker container restart $DOCKER_CONTAINER
else
	docker run --name $DOCKER_CONTAINER --env MYSQL_DATABASE=swingyDB --env MYSQL_ROOT_PASSWORD=doge -d -p 3306:3306 mysql
fi

# Wait a minute for mysql to finish initializing, and then you can run mysql_init.sh
