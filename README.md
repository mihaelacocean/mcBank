# mcBank

REST API for managing bank clients
Available operations:
- displaying user with accounts and transactions
- opening a new current account for an existing user

About the application:
-  Spring boot application running with Java 8
- In memory DB

### How to build and run:


```
git clone https://github.com/mihaelacocean/mcBank.git
cd mcBank
./mvnw spring-boot:run
http://localhost:8080/swagger-ui.html#/
```

In memory DB can be accessed here: 
http://localhost:8080/h2-console
Credentials:  https://github.com/mihaelacocean/mcBank/blob/master/src/main/resources/application.properties#L3

Tables can be found under mcbank schema


More information about running tests can be found here:
https://github.com/mihaelacocean/mcBank/blob/development/docs/UserGuide.md
