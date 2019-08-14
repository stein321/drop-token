# Drop Token

## Requirements 
 * Java 11
 * Maven 3 (use included maven wrapper if not)
 * Mongo db running on the default port  
  
## Build 
 `mvn clean install`
 
## Run App 
   `./target/drop-token.jar`
## Run Locally with auto restart on class re-compile
   `mvn spring-boot:run` 
   
   
## Running in AWS 
1. `terraform apply`
2. run infra/mongCreds.sh on mongo vm
3. Update application.properties on api vm to point to mongo with creds   

