#!/bin/bash

# Connect to a mysql server running on docker-machine named 'Char' on localhost:
# Username: root
# Password: doge

# It already has a database called swingDB

eval "$(docker-machine env Char)"
mysql -h $(docker-machine ip Char) -u root -pdoge swingyDB < setup/sql_init.sql
