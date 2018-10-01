# MANUALLY run to set up environment:
eval "$(docker-machine env Char)"
export DOCKER_MACHINE_IP=$(docker-machine ip Char)

# Some SQL quick tips:
show databases;
show tables;
describe hero_table;
select * from hero_table;

