# REST-Example
Example RESTful web service implemented with Spring Boot, JAX-RS and RxJava.
This is the version with asynchronous JAX-RS server-side request processing and with RxJava.
Please refer to the "before" branch for the version without asynchronous JAX-RS and RxJava.

To load-test this application, first start the RestExampleApplication and then start the load-tests using mvn gatling:test.
The load-test report can be found in the target/gatling directory.

The articles for which this example program was developed can be found here:
https://www.ivankrizsan.se/2016/11/19/rest-with-asynchronous-jersey-and-rxjava-part-1/
https://www.ivankrizsan.se/2016/11/25/rest-with-asynchronous-jersey-and-rxjava-part-2/
https://www.ivankrizsan.se/2016/12/17/rest-with-asynchronous-jersey-and-rxjava-part-3/
https://www.ivankrizsan.se/2016/12/29/rest-with-asynchronous-jersey-and-rxjava-part-4/
