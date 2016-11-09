import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random

/**
  * Gatling load test of the REST service.
  *
  * @author Ivan Krizsan
  */
class GatlingLoadTest extends Simulation {
    /* Simulation timing and load parameters. */
    val rampUpTimeSecs = 5
    val testTimeSecs = 20
    val noOfUsers = 5
    val noOfRequestPerSeconds = 100
    val minWaitMs = 20 milliseconds
    val maxWaitMs = 100 milliseconds

    /* Expected response HTTP status. */
    val expectedHttpStatus = 200

    val baseURL = "http://localhost:8080/rectangle"
    val baseName = "rectangle"
    val requestName = baseName + "-request"
    val scenarioName = baseName + "-scenario"

    /**
      * Random number generator that generates random numbers in the range 0-1000 that
      * are made available in the load-tests.
      */
    val randomNumberGenerator = Iterator.continually(
        Map("RandomNumber" -> Random.nextInt(1000))
    )

    val httpProtocol = http
        .baseURL(baseURL)
        .acceptHeader("application/json")
        .userAgentHeader("Gatling")

    val testScenario = scenario(scenarioName)
        .feed(randomNumberGenerator)
        .during(testTimeSecs) {
            exec(http(requestName)
                .post("/")
                .body(ElFileBody("createRectangle.json")).asJSON
                .check(status.is(expectedHttpStatus)))
        }

    setUp(
        testScenario
            .inject(atOnceUsers(noOfUsers)))
        .throttle(reachRps(noOfRequestPerSeconds) in (rampUpTimeSecs seconds), holdFor(testTimeSecs seconds))
        .protocols(httpProtocol)
}
