# Username List App HBM

### Application Overview
This App receives a username from an user and it checks in a storage (H2 Embedded Database) if the username is already exists or if is a valid new username.
It was created a REST service which receive the username as URI parameter and then check in the database whether or not the provided user already exists.
If the username doesn't exists and is complience with the pre-defined rules, then the application return TRUE and an empty suggested username list.
Otherwise, the application generate a list with some valid usernames suggestions.

### Application Configuration
  - Java 1.8_71
  - Maven 3.3.9
  - SpringBoot 1.5.3.RELEASE
  - H2 Embedded Database

### Business Rules
  - If the username is valid, the application returns a success Boolean result with empty list of suggested usernames;
  - If the username is invalid, the application returns false Boolean result with a List of at least 14 alternate suggested usernames based of the input username string;
  - The alternate usernames List is in alphabetical order;
  - Each suggest in alternate List is unique;
  - There is list of restricted words which the username should not contain;
  - The usernames in the suggested alternate list doesn't contains any restricted word;
  - The App try to generate 14 possible unique usernames. If it''s not possible, the application only retry 3 times to generate the 14 possibilities;
  - The application throws an Exception if the username provided has less than 6 characters.

### Application Build / Start
This application was built using spring boot and H2 Embedded database.
It was used Maven build manager. To build the project using Maven, just run the command inside the project home directory:
```sh
mvn clean install
```
After the build has successfully executed, a new JAR (*username-list-app-0.0.1-SNAPSHOT.jar*) file will be created inside *'target'*.
Then, to start the application, you just need to run java -jar command:
```sh
java -jar target/username-list-app-0.0.1-SNAPSHOT.jar
```
The application was configured to use H2 Embedded database. So, at the application start, the spring will automatically generate the database DDL structure executing the script *'src/main/resources/schema.sql'*. After executing this script, spring will load data inside the created tables executing the second script *'src/main/resources/data.sql'*.
Also, the H2 Client Console is available when the application is runnnig. To access the H2 Client, just access the address: [http://localhost:8080/h2-console](localhost:8080/h2-console)
To log in on H2 Client, use bellow parameters:
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:test
- User name: sa
- Password: 

### Application Performance
It was considered a few number of records of username and restricted words to implement this application. For this reason, it loads in memory all records from each table (USERNAME_LIST and RESTRICTED_WORDS). Thinking in the future, having a thousand of usernames and restricted words, this approach is not the best due a memory consumption. To solve this, we can consider change the methods *'generateValidUsernameList'* and *'generateRandomUsernameList'* to search into the database if the username already exists:
```sh
List<String> validUsernames = new ArrayList<String>();
boolean existentUsernames = usernameListRepository.getAllUsernameList();
for (int i = 0; i < UsernameUtil.USERNAME_GENERATION_RETRY
        && validUsernames.size() < UsernameUtil.USERNAME_MIN_SUGGESTION; i++){
    for (int n = 0; n < UsernameUtil.USERNAME_MIN_SUGGESTION; n++){
        String newUsername = generateUsername();
        boolean existentUsernames = usernameListRepository.findByUsername(newUsername);
        if(!existentUsernames){
            validUsernames.add(newUsername);
        }
    }
}
```
This code has worst case of 42 database queries and can be analysed against the approach of loading the entire table in memory.

