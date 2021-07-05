docker-compose -f ../src/main/docker/sonar.yml up -d
../mvnw -f ../pom.xml -Pprod clean verify sonar:sonar 