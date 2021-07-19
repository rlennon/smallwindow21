docker-compose -f ../src/main/docker/local-dev.yml up -d
mvn -f ../pom.xml spring-boot:run -Pdev
