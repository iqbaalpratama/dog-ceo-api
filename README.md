# Dog-Ceo-API

This project is REST API that consumes and process data from  [https://dog.ceo/dog-api/](https://dog.ceo/dog-api/)  using springboot 3.1.3 and use in-memory h2 database.

The following is a sequence of commands to run this program locally:
- Download `dog-api-0.0.1-SNAPSHOT.jar`  file from the target directory and 
- Run the program using the command:  `java -jar dog-api-0.0.1-SNAPSHOT.jar` from cmd/powershell, wait until program run successfully. Usually, it needs time to retrieve and saves data from external API to database. 
- Open the  `http://localhost:8081/swagger-ui/index.html`  link in your browser to test the API manually. 
- To see in-memory database schema and executing queries to check the data, open `http://localhost:8081/h2-console/` in web browser and connect with this settings: 
`JDBC URL: jdbc:h2:mem:testdb`
`User name: sa`

## Database Schema
![Database Schema of Dog Ceo REST API](https://github.com/iqbaalpratama/dog-ceo-api/blob/master/Schema.png)

