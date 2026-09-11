# Redixy

A Redis server implementation in Java, built from scratch as a learning project.

## Requirements

- Java 25
- Maven

## Run

```bash
./run.sh
```

By default it listens on port 6379. To use a different port:

```bash
./run.sh --port 6390
```

## Test

In another terminal:

```bash
redis-cli -p 6379 PING
```
