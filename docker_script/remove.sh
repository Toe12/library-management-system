#!/bin/bash

docker container stop mysqldblibrary
docker container rm mysqldblibrary
docker volume rm mysqllibrarydbvol

docker container stop redistest
docker container rm redistest
docker volume rm redistestvol