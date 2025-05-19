#!/bin/bash

set -e 

IMAGE_NAME="jonmazzh/grupo13:1.0.0"

docker build -t $IMAGE_NAME ./.