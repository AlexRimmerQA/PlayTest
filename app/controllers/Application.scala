package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def saywhat(what:String) = Action {
    if(what == "WHAT") {
      Ok(views.html.saywhat("You are a cool dude"))
    } else {
      Ok(views.html.saywhat("Maaaan, you just don't understand, get out of here"))
    }
  }

  def justSomePage() = Action {
    Ok(views.html.justsomepage())
  }

  def pageWithStuff() = Action {
    Ok(views.html.pagewithstuff())
  }

}