# README

## How to run the application?
Navigate to the root of the project via command line and execute the command: mvn spring-boot:run

## How to build the application?
Navigate to the root of the project via command line and execute the command: mvn clean package

Then run the JAR file using the command: java -jar target/test-0.0.1-SNAPSHOT.jar

## How to run the unit test?
Navigate to the root of the project via command line and execute the command: mvn test

## How to test the API?
### Get Current User Info
* Unauthenticated: curl -i http://localhost:8080/me
* Authenticated with MEMBER role: curl -i --user john.doe@localhost.xyz:abcD1234@ http://localhost:8080/me
* Authenticated with ADMIN role: curl -i --user admin@localhost.xyz:abcD1234@ http://localhost:8080/me

### Get all users
* Unauthenticated: curl -i "http://localhost:8080/users?page=0&size=20"
* Authenticated with MEMBER role: curl -i --user john.doe@localhost.xyz:abcD1234@ "http://localhost:8080/users?page=0&size=20"
* Authenticated with ADMIN role: curl -i --user admin@localhost.xyz:abcD1234@ "http://localhost:8080/users?page=0&size=20"

### Add/Remove a permission within a Role
* Unauthenticated: curl -i "http://localhost:8080/grant-permission?roleId=2&permissionId=2"
* Authenticated with MEMBER role: curl -i --user john.doe@localhost.xyz:abcD1234@ -X POST "http://localhost:8080/admin/grant-permission?roleId=2&permissionId=2"
* Authenticated with MEMBER role: curl -i --user john.doe@localhost.xyz:abcD1234@ -X DELETE "http://localhost:8080/admin/revoke-permission?roleId=2&permissionId=2"
* Authenticated with ADMIN role: curl -i --user admin@localhost.xyz:abcD1234@ -X POST "http://localhost:8080/admin/grant-permission?roleId=2&permissionId=2"
* Authenticated with ADMIN role: curl -i --user admin@localhost.xyz:abcD1234@ -X DELETE "http://localhost:8080/admin/revoke-permission?roleId=2&permissionId=2"
