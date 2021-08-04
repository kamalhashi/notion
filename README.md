# Key Technologies Used
- Embedded Mongo Database
- KeyCloak for authentication and authorization 
- Gateway Service that hides backend services from external clients
- Common Platform: a library jar that contains common shared Classes, functionalites 

# How it works

There are four services, which are:  


- Gateway Service: Gateway is built on (Spring Cloud Gateway), the responsibility of the gateway is to hide the services from external clients and to make sure all exchanges with external clients are authorized by using OAuth2 
- Key Cloack Service: OIDC protocol has been used to authenticate and authorize users, any USER who has a role of (ROLE_WRITE) can post new score reviews  
- Product Service : Responsible fetching products
- Review Service: Responsible reviews of the product

# Setup Infrustructure 

The only set up this project needs is setting up KeyCloak and creating user with permission

To setup KeyCloak run below command 

```
docker run -d --name keycloak -p 8888:8080 \
   -e KEYCLOAK_USER=notion \
   -e KEYCLOAK_PASSWORD=notion123 \
   jboss/keycloak
```

After the keycloak container start, visit the admin console of KeyCloak  (localhost:8888) and create a client named 
```
notion-client
```
While you are on the management console create two users and assign one of them a Role named "ROLE_USER"

The last step is to get the client-credentials from "notion-client" and update the gateway-service application-local.yaml file with the client-name and client-credentials



# Running Local 
To run the project locally, run the following commands 
```
mvn clean compile --settings settings.xml 
```
Once the dependencies (The common platform) has been downloaded, run below command 


```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```



## PENDING

- Run the project on docker
- Create custom theme plugin on KeyCloak 
- Push the project on cloud (Azure, AWS)
- Swagger Documentation 
- Test Coverage for all classes

