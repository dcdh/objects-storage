## What

Store any kind of file into a s3 server like real amazon S3 service or Zenko.

## How to build and run

1. run `mvn clean install verify` to build everything;
1. run `docker build -f infrastructure/src/main/docker/Dockerfile.jvm -t damdamdeo/objects-storage-jvm infrastructure/` to build docker image
1. run `docker-compose -f docker-compose-local-run.yaml up` to start the stack
1. access swagger ui via `http://0.0.0.0:8080/q/swagger-ui/`

## Limitations

1. `native` compilation is not supported by tika.

## Infra

### Zenko

`ENDPOINT` need to be defined however the `objects-storage` application will not be able to connect to zenko having this error **Connection refused**
