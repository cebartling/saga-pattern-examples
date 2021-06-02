# Saga Pattern Basic Example

## Introduction


## Implementation

### Building the Keycloak Docker image (ARM64 architecture only)
The default image for Keycloak is built for `amd64` architectures. If you're on Apple Silicon (`arm64`), you'll need to build the Keycloak image on your Apple Silicon macOS system.

1. Clone the [keycloak\keycloak-containers](https://github.com/keycloak/keycloak-containers) Git repository: `git clone git@github.com:keycloak/keycloak-containers.git`
1. `cd keycloak-containers/server`
1. `docker build . -t keycloak-arm64:13.0.0`
1. Update the `KEYCLOAK_IMAGE` variable in the `.env` file to `keycloak-arm64:13.0.0`. This will be referenced in the Docker Compose YAML file.

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

