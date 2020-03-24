#!/bin/bash
mvn sonar:sonar \
  -Dsonar.projectKey=open-mrs \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=6c24ea7af66d015db43347b9fed476dbce48efa1
