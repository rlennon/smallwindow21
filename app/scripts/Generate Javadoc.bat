call ../mvnw.cmd -f ../pom.xml clean javadoc:javadoc
call set currentPath=%cd%
call start chrome %currentPath%/../target/site/apidocs/index.html
