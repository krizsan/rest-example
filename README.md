# REST-Example
Example RESTful web service implemented with Spring Boot and JAX-RS.
This is the version with plain, synchronous, JAX-RS server-side request processing and without RxJava.

To load-test this application, first start the RestExampleApplication and then start the load-tests using mvn gatling:test.
The load-test report can be found in the target/gatling directory.
