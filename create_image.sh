#!/bin/bash

# This script makes a Docker image for the application.

set -e 

IMAGE_NAME="robert0711/grupo13:1.0.0"

docker build -t $IMAGE_NAME ./.

