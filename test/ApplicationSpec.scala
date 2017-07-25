import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/boum")).get
      status(result) must equalTo(NOT_FOUND)
    }

    "be redirected when going to the index page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/")).get
      status(result) must equalTo(SEE_OTHER)
    }

    "render the dynamic page with a parameter" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/dynamicparampage/Tester")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Current parameter = Tester")
    }

    "render the dynamic page with an alternative parameter" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/dynamicparampage/WeLoveTesting")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Current parameter = WeLoveTesting")
    }

    "render the static page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/staticparampage/static")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Param passed in = THIS IS STATIC")
    }

    "render the default parameter page with a default value" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/defaultparampage")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Param passed in = Hello?")
    }

    "render the default parameter page with a assigned value" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/defaultparampage?param=Hey!")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Param passed in = Hey!")
    }

    "render the set parameter page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/setparampage")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Here it is --> Hello")
    }

    "render the optional parameter page without a parameter" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/optparampage")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("No optional parmeter was set")
    }

    "render the optional parameter page with a parameter" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/optparampage?param=Set!")).get
      status(result) must equalTo(OK)
      contentType(result) must beSome.which(_ == "text/html")
      contentAsString(result) must contain("Parameter passed in was: Set!")
    }

    "render the default not found page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/notfoundpage")).get
      status(result) must equalTo(NOT_FOUND)
    }

    "render the default error page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/errorpage")).get
      status(result) must equalTo(INTERNAL_SERVER_ERROR)
    }

    "render the default todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/todopage")).get
      status(result) must equalTo(NOT_IMPLEMENTED)
    }

    "render the action todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/actiontodo")).get
      status(result) must equalTo(NOT_IMPLEMENTED)
    }

    "render the action todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/actionnotfound")).get
      status(result) must equalTo(NOT_FOUND)
    }

    "render the action todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/actionbadrequest")).get
      status(result) must equalTo(BAD_REQUEST)
    }

    "render the action todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/actionservererr")).get
      status(result) must equalTo(INTERNAL_SERVER_ERROR)
    }

    "render the action todo page" in new WithApplication{
      val result = route(app, FakeRequest(GET, "/actionanystatus")).get
      status(result) must equalTo(488)
    }

    

  }
}
