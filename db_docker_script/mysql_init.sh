#!/bin/bash

docker run --name mysqldblibrary -v mysqllibrarydbvol:/var/lib/mysql -p 3307:3306 -e MYSQL_USER=mysql -e MYSQL_PASSWORD=mysql -e MYSQL_DATABASE=test -e MYSQL_ROOT_PASSWORD=password -e MYSQL_ROOT_HOST=% -d mysql/mysql-server:latest
