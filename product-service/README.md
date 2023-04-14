Applicaiton resource: https://www.youtube.com/watch?v=mPPhcU7oWDU

Maven Project

MONGODB setup: https://www.youtube.com/watch?v=GvPhube6Mls
1. Download MongoDB
2. Set springboot dependency: 
   1. Spring Data MongoDB NoSQL
   2. Spring Web
   3. TestContainers

3. Mongodb config setup src/resources: application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/product-service

4. Create DB schema product-service in MongoDB
5. After the Get and Post functionality is completed, run the ProductService Applicaiton
6. Test the service from postman
   1. POST: http://localhost:8080/api/product
   2. BODY:
      {
         "name": "Iphone 13",
         "description": "Iphone 13",
         "price": 1200
         }
   3. GET: http://localhost:8080/api/product


TEST:
Testing Integration testing, not unit test using testcontainers for junit. It uses dockercontainer and useful for Selenium also.
https://www.testcontainers.org/modules/databases/mongodb/

We use Junit5 to write the test
https://www.testcontainers.org/test_framework_integration/junit_5/

Springboot testing crashcourse: https://www.youtube.com/watch?v=aPoJPESMJBk