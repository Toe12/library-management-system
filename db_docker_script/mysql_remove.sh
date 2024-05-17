#!/bin/bash

docker container stop mysqldblibrary
docker container rm mysqldblibrary

if [ "$1" == "-t" ]; then
	docker volume rm mysqllibrarydbvol
fi
