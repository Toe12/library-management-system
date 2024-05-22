#!/bin/bash

docker run --name mysqldblibrary -v mysqllibrarydbvol:/var/lib/mysql -p 3307:3306 -e MYSQL_USER=mysql -e MYSQL_PASSWORD=mysql -e MYSQL_DATABASE=library -e MYSQL_ROOT_PASSWORD=password -e MYSQL_ROOT_HOST=% -d mysql/mysql-server:latest
docker run --name redistest -v redistestvol:/data -p 6379:6379 -d redis:latest
