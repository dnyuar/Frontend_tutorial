package services

import connector.BackendConnector
import javax.inject.Inject
import models.{RequestModel, ResponseModel}

import scala.concurrent.{ExecutionContext, Future}

class PersonService @Inject()(connector: BackendConnector ) {

  def getDetails(request: RequestModel)(implicit ec: ExecutionContext): Future[ResponseModel] ={
    connector.getDetails(request)
  }
}
