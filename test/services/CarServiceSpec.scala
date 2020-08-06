package services


import connector.BackendConnector
import models.{RequestModel, ResponseModel}
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration.Inf

class CarServiceSpec extends PlaySpec with MockFactory {

  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  val mockConnector = mock[BackendConnector]
  val service: CarService = new CarService(mockConnector)

  ".getDetails" should {

    "return a response model" in {

      (mockConnector.getDetails(_: RequestModel)(_: ExecutionContext))
        .expects(RequestModel("Peter"), *)
        .returning(Future.successful(ResponseModel("Audi", "Blue", 10)))

      val request = RequestModel("Peter")
      val response: ResponseModel = Await.result(service.getDetails(request), Inf)

      response mustBe ResponseModel("Audi", "Blue", 10)

    }

  }

}
