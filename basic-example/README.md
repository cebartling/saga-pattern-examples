# Saga Pattern Basic Example

## Introduction


## Implementation

### Docker Compose

All infrastructure services are maintained by Docker Compose. 

#### Configure environment
Create a `.env` file in this project's root directory, using the appropriate `.env-*` for your specific system architecture. Therefore, if you're working on an Apple Silicon macOS machine, copy the contents of `.env-arm64` file to the `.env` file. Same applies to AMD64 architecture.

#### Starting the services

```shell
docker compose up -d
```

#### Stopping the services

```shell
docker compose down -v --remove-orphans --timeout 30
```

