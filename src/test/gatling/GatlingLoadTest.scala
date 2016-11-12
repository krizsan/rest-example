import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random
import java.net.HttpURLConnection

/**
  * Gatling load test of the REST service.
  * The load tests consists of requests that creates different types of entities and
  * requests that retrieve all entities of a type. These requests are issued in
  * a round-robin fashion.
  *
  * @author Ivan Krizsan
  */
class GatlingLoadTest extends Simulation {
    /* Simulation timing and load parameters. */
    val rampUpTimeSecs = 5
    val testTimeSecs = 30
    val noOfUsers = 300
    val noOfRequestPerSeconds = 1500

    val baseURL = "http://localhost:8080"
    val circlesResourcePath = "/circles"
    val rectanglesResourcePath = "/rectangles"
    val drawingsResourcePath = "/drawings"

    /* Request that creates one rectangle and verifies the outcome. */
    object CreateRectangle {
        val request = exec(http("CreateRectangle")
            .post(rectanglesResourcePath)
            .body(ElFileBody("createRectangle.json")).asJSON
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

    /* Request that creates one rectangle and verifies the outcome. */
    object CreateCircle {
        val request = exec(http("CreateCirlce")
            .post(circlesResourcePath)
            .body(ElFileBody("createCircle.json")).asJSON
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

    /* Request that creates one drawing that contains a number of shapes and verifies the outcome. */
    object CreateDrawing {
        val request = exec(http("CreateDrawing")
            .post(drawingsResourcePath)
            .body(ElFileBody("createDrawing.json")).asJSON
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

    /* Request that retrieves all rectangles and verifies the outcome. */
    object RetrieveAllRectangles {
        val request = exec(http("RetrieveAllRectangles")
            .get(rectanglesResourcePath)
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

    /* Request that retrieves all circles and verifies the outcome. */
    object RetrieveAllCircles {
        val request = exec(http("RetrieveAllCircles")
            .get(circlesResourcePath)
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

    /* Request that retrieves all drawings and verifies the outcome. */
    object RetrieveAllDrawings {
        val request = exec(http("RetrieveAllDrawings")
            .get(drawingsResourcePath)
            .check(status.is(HttpURLConnection.HTTP_OK)))
    }

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

    val testScenario = scenario("LoadTest")
        .feed(randomNumberGenerator)
        .during(testTimeSecs) {
            exec(
                roundRobinSwitch(
                    CreateRectangle.request,
                    CreateCircle.request,
                    CreateDrawing.request,
                    RetrieveAllRectangles.request,
                    RetrieveAllCircles.request,
                    RetrieveAllDrawings.request
                )
            )
        }

    setUp(
        testScenario
            .inject(atOnceUsers(noOfUsers)))
        .throttle(
            reachRps(noOfRequestPerSeconds) in (rampUpTimeSecs seconds),
            holdFor(testTimeSecs seconds))
        .protocols(httpProtocol)
}
