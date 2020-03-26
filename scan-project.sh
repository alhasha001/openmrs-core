#!/bin/bash
mvn sonar:sonar \
  -Dsonar.projectKey=org.openmrs:openmrs \
  -Dsonar.host.url=http://localhost:9000/ \
  -Dsonar.login=admin \
  -Dsonar.password=admin
