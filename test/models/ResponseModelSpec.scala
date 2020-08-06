package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsObject, Json}

class ResponseModelSpec extends PlaySpec {

  val validJson: JsObject = Json.obj("make" -> "Audi", "colour" -> "Blue", "age" -> 10)

  val validModel: ResponseModel = ResponseModel("Audi","Blue",10)

  "ResponseModel" should {

    "successfully read from JSON" in {
      validJson.as[ResponseModel] mustBe validModel
    }
  }



}
