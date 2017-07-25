
package controllers

import play.api.mvc._

class Application extends Controller {

  val strangeCode = 488

  def index: Action[AnyContent] = Action {
    Redirect("/setparampage")
  }

  def dynamicParamPage(setParam: String): Action[AnyContent] = Action {
    Ok(views.html.dynamicparampage(setParam))
  }

  def staticParamPage(setParam: String): Action[AnyContent] = Action {
    Ok(views.html.staticparampage(setParam))
  }

  def defaultParamPage(defParam: String): Action[AnyContent] = Action {
    Ok(views.html.defaultparampage(defParam))
  }

  def setParamPage(setParam: String): Action[AnyContent] = Action {
    Ok(views.html.setparampage(setParam))
  }

  def optParamPage(optParam: Option[String]): Action[AnyContent] = Action {
    optParam match {
      case Some(x) => Ok(views.html.optparampage("Parameter passed in was: " + x))
      case None => Ok(views.html.optparampage("No optional parmeter was set"))
    }
  }

  def reversePage(): Action[AnyContent] = Action {
    Redirect(routes.Application.defaultParamPage())
  }

  def actionToDo(): Action[AnyContent] = TODO

  def actionNotFound(): Action[AnyContent] = Action {
    NotFound(<h1>Unable to find page</h1>)
  }

  def actionBadRequest(): Action[AnyContent] = Action {
    BadRequest(<h1>Bad Request</h1>)
  }

  def actionServErr(): Action[AnyContent] = Action {
    InternalServerError("Oops, it appears there was a problem")
  }

  def actionAnyStatus(): Action[AnyContent] = Action {
    Status(strangeCode)("Strange Response Type")
  }

  def addCookie(key: String, value: String): Action[AnyContent] = Action {
    Ok("Adding cookie: Key=" + key + ", Value=" + value).withCookies(
      Cookie(key, value)
    )
  }

  def removeCookies(key: String): Action[AnyContent] = Action {
    Ok("Removing all cookies made here").discardingCookies(DiscardingCookie(key))
  }

  def getCookie(key: String): Action[AnyContent] = Action {
    request => request.cookies.get(key).map{value => Ok(value.value)}.getOrElse(Ok("Cookie Not Set"))
  }

  def addSessionData(key: String, value: String): Action[AnyContent] = Action { request =>
    Ok("Adding session: Key=" + key + ", Value=" + value).withSession(
      request.session + (key -> value)
    )
  }

  def removeSessionData(key: String): Action[AnyContent] = Action { request =>
    Ok("Removing session made here").withSession(
      request.session - key
    )
  }

  def getSessionData(key: String): Action[AnyContent] = Action {
    request => request.session.get(key).map{value => Ok(value)}.getOrElse(Ok("Session Data Not Found"))
  }

  def addFlash(key: String, value: String): Action[AnyContent] = Action {
    Redirect("/getflash/key").flashing(
      key -> value
    )
  }

  def getFlash(key: String): Action[AnyContent] = Action { implicit request =>
    Ok {
      request.flash.get(key).getOrElse("Unable to retrieve the data from specified key")
    }
  }
}
