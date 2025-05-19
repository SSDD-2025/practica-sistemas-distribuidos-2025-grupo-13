#!/bin/bash

set -e

COMPOSE_FILES="-f docker-compose.local.yml -f docker-compose.prod.yml"

docker compose $COMPOSE_FILES push jonmazzh/grupo13:1.0.0


