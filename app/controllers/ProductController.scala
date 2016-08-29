package controllers

import scala.concurrent.ExecutionContext

import dal.ProductRepository
import javax.inject.Inject
import models.Location
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.Controller
import services.Catalogue
import services.CostumerLocation

class ProductController @Inject() (val messagesApi: MessagesApi)(implicit ec: ExecutionContext) extends Controller with I18nSupport {

  val repo: ProductRepository = ProductRepository

  def index = Action {
    Ok(views.html.index())
  }

  def confirmation = Action {
    Ok(views.html.confirmation())
  }

  private def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }

  /**
   * A REST endpoint that gets all the products as JSON.
   */
  def getProducts = Action { implicit request =>
    val cid: Option[Int] = request.cookies.get("customerID") match {
      case Some(cookie) => toInt(cookie.value)
      case None         => None
    }

    val location = cid match {
      case Some(elem) => CostumerLocation.getLocation(elem);
      case None       => None
    }

    location match {
      case Some(elem) => {
        val products = Catalogue.getProducts(elem)
        Ok(Json.toJson(products))
      }
      case None => Ok(Json.toJson(""))
    }

  }

  def placeOrder = Action(parse.json) { implicit request =>
    //do something complex to place order...

    val cid = (request.body \ "customerID").asOpt[Long]
    val products = (request.body \ "products")

    val productsAsArray = products.asOpt[Array[Int]].getOrElse(Array.empty)
    val productsAsString = productsAsArray mkString (", ")

    cid match {
      case Some(id) => {
        val status = "Received order from client " + id + " for the following items: " + productsAsString
        Ok(Json.toJson(Map("status" -> status)))
      }
      case None => BadRequest(Json.toJson(
        Map("status" -> "Something went wrong")))
    }

  }

}

