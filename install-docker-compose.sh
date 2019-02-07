#!/bin/sh

url="https://github.com/docker/compose/releases/download/1.11.2/docker-compose-$(uname -s)-$(uname -m)";
docker_compose_dest="/usr/local/bin/docker-compose";
sudo curl -L ${url} -o ${docker_compose_dest};
sudo chmod +x ${docker_compose_dest};