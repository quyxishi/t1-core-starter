# core-starter
> T1-debut task w/ basic starter + audit via Kafka

## Getting Started
```shell
git clone https://github.com/quyxishi/t1-core-starter
cd ./t1-core-starter
```

### *via `docker-compose.yaml`*
```shell
sudo docker-compose up --build
```

### *via `Dockerfile`*
* #### Build
```shell
sudo docker build --tag synth-starter .
```

* #### Running
```shell
sudo docker-compose up -d kafka
sudo docker run --rm -p 8080:8080 -p 8082:8082 \
            -e SPRING_KAFKA_BOOTSTRAP_SERVERS='kafka:9092' \
            --network t1-core-starter_default \
            --link kafka:kafka \
            synth-starter
```

## Development
* #### Build
```shell
./gradlew clean build --no-daemon
```

* #### Running
```shell
./gradlew bootRun
```
