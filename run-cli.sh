#!/bin/bash

./mvnw -DskipTests=true clean install

java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 cli/target/fire-res-cli.jar -c "application.conf"