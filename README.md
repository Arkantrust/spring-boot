# spring-boot template

Template repository for any web app using [Spring Boot 3](https://spring.io/projects/spring-boot), [java 21](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html) and [PostgreSQL 16](https://www.postgresql.org/).


## Running with docker

``` bash
docker compose up # Add -d to run in background
```

NOTE: The `data/` directory holds the DB data from the container to persist it if it restarts. But it's been ignored by git.

Kill the containers with:

``` bash
docker compose down
```

## Development

It's still possible to run the app without docker, in fact it's recommended for development.

### Database

Run the DB using docker:

``` bash
docker run -d --name spring -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:16
```

This will install the PostgreSQL 16 docker image automatically. And you can the access it with:

``` bash
docker exec -it spring psql -U postgres
```


It's also possible to use a local DB by installing PostgreSQL on your machine.

Specify the environment variables in the `application.yaml` file:

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://127.0.0.1:5432/app
    username: postgres
    password: postgres
```

It's mandatory to create the database `app` before running the app.

``` SQL
CREATE DATABASE app;
```

NOTE: `data/` holds the data from the container to persist it if it restarts. But it's been ignored by git.

## Running the app

``` bash
mvn clean package # build the app

java -jar target/spring-0.0.1.jar # run the app
```