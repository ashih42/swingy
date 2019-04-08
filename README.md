# swingy
A top-down role-playing game in Java using [Swing](https://en.wikipedia.org/wiki/Swing_(Java)). (42 Silicon Valley)

<p float="left">
  <img src="https://github.com/ashih42/swingy/blob/master/Screenshots/screenshot1.jpg" width="540" />
</p>

## Prerequisites

You have `Maven` and `Java 9 JDK` installed.  `Docker` and `MySQL` are optional.

## (Optional) Set up MySQL database in Docker

These scripts assume you have created a docker-machine named `Char`.

```
# If necessary, create a new docker-machine.
docker-machine create --driver virtualbox Char

# Start docker-machine, download and initialize mysql container.
./setup/docker_init.sh

# WAIT a minute for mysql to finish initialization,
# and then run this script to populate the database.
./setup/mysql_init.sh
```

The game will still run if it cannot find the MySQL server, but you will not be able to save your progress.

## Compiling

```
mvn clean package
```

## Running

```
./run.sh (console|gui)
```
