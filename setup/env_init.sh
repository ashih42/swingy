# source env_init.sh

eval "$(docker-machine env Char)";
export DOCKER_MACHINE_IP=$(docker-machine ip Char);
