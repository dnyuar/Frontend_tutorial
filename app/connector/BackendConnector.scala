package connector

import javax.inject.Inject
import models.{RequestModel, ResponseModel}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.mvc.Http.Status._

import scala.concurrent.{ExecutionContext,Future}

class BackendConnector @Inject()(ws: WSClient) {

  val baseUrl = "http://localhost:9000"

  def getDetails(requestModel:RequestModel)(implicit ec: ExecutionContext): Future[ResponseModel] = {
    ws.url(baseUrl).post(Json.toJson(requestModel)).map { response =>
      response.status match {
        case OK => response.json.as[ResponseModel]
        case BAD_REQUEST => ResponseModel("unknown","unknown",0)
        case unknownStatus => ResponseModel("Unexpected Status code", "unexpected", unknownStatus)
      }

    }

  }
}
