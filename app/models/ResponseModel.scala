package models

import play.api.libs.json.{Json, OWrites, Reads}

case class ResponseModel(
                         make: String,
                         colour:String,
                         age:Int
                       )

object ResponseModel {
  implicit val reads: Reads[ResponseModel] = Json.reads[ResponseModel]
}