# URL Shortner

A simple URL shortner using Spring Boot and PostgreSQL.

## Project Requirements

- Java 21+
- Maven 3.8.4+
- Docker (Optional)
- PostgreSQL (Preferred version: 15)

## Project Setup

- Start the PostgreSQL (default port is 5432)
- Create a user with a password
- Create a database and give access of the database to the newly created user
- Edit the [DataSourceConfig.java](src/main/java/io/github/navjotsrakhra/urlshortner/config/DataSourceConfig.java) file
  with the database
  details as follows
    - url: jdbc:postgresql://localhost:5432/{the database name}
    - username: {the username}
    - password: {the password}
    - Example snippet:
    - ```java
        public DataSource getDataSource() {
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName("org.postgresql.Driver");
            dataSourceBuilder.url("jdbc:postgresql://localhost:5432/url_shortner");
            dataSourceBuilder.username("test");
            dataSourceBuilder.password("test");
            return dataSourceBuilder.build();
      }
        ```

## Running the Project

### To run the project without building

```shell
mvn spring-boot:run
```

## To build the project and run

```shell
mvn clean package
cd target
java -jar app.jar 
```
