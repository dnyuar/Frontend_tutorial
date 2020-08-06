package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}

class RequestModelSpec extends PlaySpec {

  val validJson: JsObject = Json.obj(
    "name" -> "Peter"
  )

  val validModel = RequestModel("Peter")

  "Request model" should {

    "write to Json" in {
      Json.toJson(validModel) mustBe validJson
    }

  }
}
