package controllers

import play.api._
import play.api.mvc._
import play.mvc.Http
import sun.awt.SunHints.Key

class Application extends Controller {

  def index = Action {
    Redirect("/setparampage")
  }

  def dynamicParamPage(setParam: String) = Action {
    Ok(views.html.dynamicparampage(setParam))
  }

  def staticParamPage(setParam: String) = Action {
    Ok(views.html.staticparampage(setParam))
  }

  def defaultParamPage(defParam: String) = Action {
    Ok(views.html.defaultparampage(defParam))
  }

  def setParamPage(setParam: String) = Action {
    Ok(views.html.setparampage(setParam))
  }

  def optParamPage(optParam: Option[String]) = Action {
    optParam match {
      case Some(x) => Ok(views.html.optparampage(x))
      case None => Ok(views.html.optparampage("No optional parmeter was set"))
    }
  }

  def reversePage() = Action {
    Redirect(routes.Application.defaultParamPage())
  }

  def actionToDo() = TODO

  def actionNotFound() = Action {
    NotFound(<h1>Unable to find page</h1>)
  }

  def actionBadRequest() = Action {
    BadRequest(<h1>Bad Request</h1>)
  }

  def actionServErr() = Action {
    InternalServerError("Oops, it appears there was a problem")
  }

  def actionAnyStatus() = Action {
    Status(488)("Strange Response Type")
  }

  def addCookie(key: String, value: String) = Action {
    Ok("Adding cookie: Key=" + key + ", Value=" + value).withCookies(
      Cookie(key, value)
    )
  }

  def removeCookies(key: String) = Action {
    Ok("Removing all cookies made here").discardingCookies(DiscardingCookie(key))
  }

  def getCookie(key: String) = Action {
    request => request.cookies.get(key).map{value => Ok(value.value)}.getOrElse(Ok("Cookie Not Set"))
  }

  def addSessionData(key: String, value: String) = Action { request =>
    Ok("Adding session: Key=" + key + ", Value=" + value).withSession(
      request.session + (key -> value)
    )
  }

  def removeSessionData(key: String) = Action { request =>
    Ok("Removing session made here").withSession(
      request.session - key
    )
  }

  def getSessionData(key: String) = Action {
    request => request.session.get(key).map{value => Ok(value)}.getOrElse(Ok("Session Data Not Found"))
  }

  def addFlash(key: String, value: String) = Action {
    Ok("Adding flash: Key=" + key + ", Value=" + value).flashing(
      key -> value
    )
  }

  def getFlash(key: String) = Action { implicit request =>
    Ok {
      request.flash.get(key).getOrElse("Unable to retrieve the data from specified key")
    }
  }

}