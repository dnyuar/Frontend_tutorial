package connector

import com.github.tomakehurst.wiremock.client.WireMock
import org.scalatestplus.play.PlaySpec
import connector.BackendConnector
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import utils.WireMockSetup
import com.github.tomakehurst.wiremock.client.WireMock._
import models.{RequestModel, ResponseModel}

import scala.concurrent.duration.Duration.Inf
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import play.mvc.Http.Status._

class BackendConnectorSpec extends PlaySpec with GuiceOneServerPerSuite with BeforeAndAfterAll with WireMockSetup{

  override def beforeAll() ={
    super.beforeAll()
    wireMockServer.start()
    WireMock.configureFor(wireMockHost, wireMockPort)
  }

  override def afterAll() = {
    wireMockServer.stop()
    app.stop()
    super.afterAll()
  }

  def mockCall(responseCode:Int, requestJson: JsValue, response: JsValue) = {
    stubFor(post("/").withRequestBody(
      equalToJson(Json.stringify(requestJson))
    ).willReturn(
      aResponse()
        .withStatus(responseCode)
        .withBody(
        Json.stringify(response)
      )
    ))
  }

  val wsClient: WSClient = app.injector.instanceOf[WSClient]

  val connector = new BackendConnector(wsClient)

  ".getDetails" should {

    "return some json" when {

      "a correct response is given" in {

        val requestJson = Json.obj("name" -> "Peter")
        val responseModel = Json.obj("make" -> "Audi","colour"-> "Blue","age"-> 10)

        mockCall(OK, requestJson, responseModel)

        val request = RequestModel("Peter")
        val response: ResponseModel =  Await.result(connector.getDetails(request),Inf)

        response mustBe ResponseModel("Audi", "Blue", 10)

      }

      "a BadRequest is returned" in {

        val requestJson = Json.obj("name" -> "Peter")

        mockCall(BAD_REQUEST, requestJson, Json.obj())

        val request = RequestModel("Steve")

        val response = Await.result(connector.getDetails(request), Inf)

        response mustBe ResponseModel("unknown", "unknown",0)
      }

      "an unexpected status is returned" in {

        val requestJson = Json.obj("name"->"Alan")

        mockCall(OK, requestJson, Json.obj())

        val request = RequestModel("Alan")

        val response =  Await.result(connector.getDetails(request),Inf)

        response mustBe ResponseModel("Unexpected status code", "unexpected",  )
      }
    }
  }

}
