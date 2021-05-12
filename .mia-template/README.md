# mia_template_service_name_placeholder

Welcome to Java Helidon example service for Mia-Platform!

## How to develop this service

This example just exposes hello endpoint.

### Run locally

To run locally this example just run the

```bash
helidon dev
```

To launch tests locally

```bash
mvn test
```

To build it

```bash
mvn clean package
```

To force mvn package update

```bash
mvn clean install -U
```

### Build Docker Image

Build the Docker Image

```
docker build -t helidon-example .
```

Start the application:

```
docker run --rm -e server.port=8080 -p 8080:8080 helidon-example:latest
```

### Build Native Docker Image

Build the "native" Docker Image

```
docker build -t helidon-example-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -e server.port=8080 -p 8080:8080 helidon-example-native:latest
```

### Routes

The following routes are exposed

- [http://localhost:3000/hello]() - hello controller
- [http://localhost:3000/-/ready]() - the service is ready (used by k8s)
- [http://localhost:3000/-/healthz]() - the service is healthy (used by k8s)
- [http://localhost:3000/documentation/json]() - the Open API 3 specification: default schema is in yaml, a client can specify Accept: as either application/vnd.oai.openapi+json or application/json to request JSON.

### Tag new project version

Please use the `tag.sh` to update the `pom.xml` project version and commit release to git.

Respect the following syntax to invoke the script:

```bash
./tag.sh [options] [rc]
```

According to [semver](https://semver.org/), *options* could be:

- _major_ version when you make incompatible API changes
- _minor_ version when you add functionality in a backwards-compatible manner
- _patch_ version when you make backwards-compatible bug fixes.

According to Mia-Platform release process *rc* could be:

- _rc_ add `-rc` to your release tag
- omitted

#### Promote `rc` release

When your service is ready to production you can promote your rc version invoking the scritp with `promote` option.

```bash
./tag.sh promote
```

#### Push changes

Don't forget to push commit and tag:

```bash
git push
git push --tags
```

#### Examples

Assuming your current version is `v1.2.3`

|command   | result  |
|---|---|
|`./tag.sh major`   |`v2.0.0`   |
|`./tag.sh minor`   |`v1.3.0`   |
|`./tag.sh patch`   |`v1.2.4`   |
|`./tag.sh major rc`   |`v2.0.0-rc`   |
|`./tag.sh minor rc`   |`v1.3.0-rc`   |
|`./tag.sh patch rc`   |`v1.2.4-rc`   |

Assuming your current version is `v1.2.3-rc`

|command   | result  |
|---|---|
|`./tag.sh promote`   |`v1.2.3`|
