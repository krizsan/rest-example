# REST-Example
Example RESTful web service implemented with Spring Boot, Spring WebFlux and Reactor.<br/>
This is the version with Spring WebFlux replacing asynchronous JAX-RS and with Reactor replacing RxJava.<br/>
Please refer to the "before" branch for the version without asynchronous JAX-RS and RxJava.<br/>
Please refer to the "rxjava-after" branch for the version with asynchronous JAX-RS and RxJava.<br/>
<br/>
To load-test this application, first start the RestExampleApplication and then start the load-tests using mvn gatling:test.<br/>
The load-test report can be found in the target/gatling directory.<br/>
<br/>
The original articles for which this example program was developed can be found here:<br/>
https://www.ivankrizsan.se/2016/11/19/rest-with-asynchronous-jersey-and-rxjava-part-1/<br/>
https://www.ivankrizsan.se/2016/11/25/rest-with-asynchronous-jersey-and-rxjava-part-2/<br/>
https://www.ivankrizsan.se/2016/12/17/rest-with-asynchronous-jersey-and-rxjava-part-3/<br/>
https://www.ivankrizsan.se/2016/12/29/rest-with-asynchronous-jersey-and-rxjava-part-4/<br/>

Note that these articles do not cover the Spring WebFlux and Reactor version.
