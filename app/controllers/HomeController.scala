package controllers

import javax.inject._
import models.RequestModel
import play.api._
import play.api.mvc._
import services.PersonService
import models.RequestModel

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                              val service: PersonService,
                               implicit val ec: ExecutionContext
                              ) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(name:String) = Action.async { implicit request: Request[AnyContent] =>

    val requestModel = RequestModel(name)
    println("------------------------------"+requestModel)

   val a = service.getDetails(requestModel).map{ result =>
      Ok(views.html.index(result))

    }
    a

  }
}
