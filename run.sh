#!/bin/bash

# Export env variable containing IP address of mysql server
eval "$(docker-machine env Char)";
export DOCKER_MACHINE_IP=$(docker-machine ip Char);

# Run the game in a jar file
java -jar target/swingy-1.0-SNAPSHOT.jar $@
