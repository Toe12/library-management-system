#!/bin/bash

docker container stop mysqldblibrary
docker container rm mysqldblibrary
docker volume rm mysqllibrarydbvol

