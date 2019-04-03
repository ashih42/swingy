# swingy
A top-down role-playing game in Java using Swing library. (42 Silicon Valley)

## Prerequisites

You have `Maven` and `Java JDK` installed.  `Docker` and `MySQL` are optional.

## (Optional) Set up MySQL database in Docker

These scripts assume you have started a docker-machine named `Char`.

```
source setup/env_init.sh;
./setup/docker_init.sh;
./setup/mysql_init.sh;
```

The game will still run if it cannot find the MySQL server, but you will not be able to save your progress.

## Compiling

```
./mvn clean package
```

## Running

```
./run.sh (console|gui)
```
