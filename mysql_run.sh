#!/bin/bash

eval "$(docker-machine env Char)"
mysql -h $(docker-machine ip Char) -u root -pdoge swingyDB < sql_init.sql
