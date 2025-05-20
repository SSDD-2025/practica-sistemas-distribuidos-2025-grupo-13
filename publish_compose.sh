#!/bin/bash

set -e

COMPOSE_FILES=" -f docker-compose.prod.yml"

docker compose $COMPOSE_FILES publish jonmazzh/grupo13:1.0.0 --with-env


