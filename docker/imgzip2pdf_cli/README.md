# Usage

## Setup

Replace UID (`1000`) and GID (`1000`) in `Dockerfile` and `docker-compose.yml` with yours.

Put target Zip files in this directory.

## With Docker

To build image:

    docker build -f Dockerfile -t imgzip2pdf-cli ../..

To run:

    docker run -it --rm -u $(id -u):$(id -g) -v $PWD:/workspace -w /workspace imgzip2pdf-cli target.zip

## With Docker-Compose

To run:

    docker-compose run --rm ws target.zip
