docker-compose -f ../src/main/docker/sonar.yml up -d
../mvnw.cmd -f ../pom.xml -Pprod clean verify sonar:sonar 