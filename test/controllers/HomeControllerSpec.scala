package controllers

import models.{RequestModel, ResponseModel}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import services.CarService

import scala.concurrent.{ExecutionContext, Future}
import org.scalamock.scalatest.MockFactory
import play.api.mvc.{AnyContent, Result}


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockFactory  {

  val ec: ExecutionContext = ExecutionContext.Implicits.global

  val mockService: CarService = mock[CarService]
  val controller = new HomeController(stubControllerComponents(), mockService, ec)

  def mockServiceCall = (mockService.getDetails(_: RequestModel)(_: ExecutionContext))
    .expects(RequestModel("Peter"),*)
    .returning(Future.successful(ResponseModel("Audi","Blue",10)))


  ".index" should {

    "return a response" when {

      "a valid input is given" which {

        val request: FakeRequest[AnyContent] = FakeRequest()

        "returns a 200" in {
          mockServiceCall

          val response: Future[Result] = controller.index("Peter")(request)
          status(response) mustBe OK
        }

        "contains specific page elements" in {
          mockServiceCall

          val response: Future[Result] = controller.index("Peter")(request)
          val body = contentAsString(response)

          body must include("Audi")
          body must include("Blue")
          body must include("10")

        }
      }
    }


  }
}
