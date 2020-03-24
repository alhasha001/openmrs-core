#!/bin/bash
mvn sonar:sonar \
  -Dsonar.projectKey=open-mrs \
  -Dsonar.host.url=http://localhost:9000/ \
  -Dsonar.login=admin \
  -Dsonar.password=admin
