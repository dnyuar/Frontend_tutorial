package models

import play.api.libs.json.{Json, OWrites, Reads}

case class RequestModel(
                        name:String
                    )

object RequestModel {
  implicit val write: OWrites[RequestModel] = Json.writes[RequestModel]
  implicit val reads: Reads[RequestModel] = Json.reads[RequestModel]
}