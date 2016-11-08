import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
  * Gatling load test of the REST service.
  *
  * @author Ivan Krizsan
  */
class GatlingLoadTest extends Simulation {
    /* Simulation timing and load parameters. */
    val rampUpTimeSecs = 20
    val testTimeSecs = 60
    val noOfUsers = 200
    val noOfRequestPerSeconds = 600
    val minWaitMs = 20 milliseconds
    val maxWaitMs = 100 milliseconds

    /* Request HTTP body contents. */
    val requestBody = "test1"
    /* Expected response HTTP status. */
    val expectedHttpStatus = 200

    val baseURL = "http://localhost:8080/rectangle"
    val baseName = "rectangle"
    val requestName = baseName + "-request"
    val scenarioName = baseName + "-scenario"

    val httpProtocol = http
        .baseURL(baseURL)
        .acceptHeader("application/json")
        .userAgentHeader("Gatling")

    val testScenario = scenario(scenarioName)
        .during(testTimeSecs) {
            exec(http(requestName)
                .get("/")
                .check(status.is(expectedHttpStatus)))
        }

    setUp(
        testScenario
            .inject(atOnceUsers(noOfUsers)))
        .throttle(reachRps(noOfRequestPerSeconds) in (rampUpTimeSecs seconds), holdFor(testTimeSecs seconds))
        .protocols(httpProtocol)
}
