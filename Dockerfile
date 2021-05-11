# 1st stage, build the app
FROM maven:3.6-jdk-11 as build

WORKDIR /build

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn package -Dmaven.test.skip -Declipselink.weave.skip

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -Dmaven.test.skip

# 2nd stage, build the runtime image
FROM openjdk:11-jre-slim

LABEL maintainer="%CUSTOM_PLUGIN_CREATOR_USERNAME%" \
      name="mia_template_service_name_placeholder" \
      description="%CUSTOM_PLUGIN_SERVICE_DESCRIPTION%" \
      eu.mia-platform.url="https://www.mia-platform.eu" \
      eu.mia-platform.version="0.1.0"\
      eu.mia-platform.language="java"

WORKDIR /build

ARG COMMIT_SHA=<not-specified>
ARG BUILD_FILE_NAME=mia_template_service_name_placeholder

RUN echo "service-name: ${COMMIT_SHA}" >> ./commit.sha

# Copy the binary built in the 1st stage
COPY --from=build /build/target/${BUILD_FILE_NAME}.jar ./application.jar
COPY --from=build /build/target/libs ./libs

USER 1000

CMD ["java", "-jar", "application.jar"]
