package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import models._
import dal._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._
import dal.ProductRepository
import dal.ProductRepository
import play.mvc.Http
import services.CostumerLocation
import services.Catalogue
import play.api.libs.json.JsValue
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess

class ProductController @Inject() (val messagesApi: MessagesApi)(implicit ec: ExecutionContext) extends Controller with I18nSupport {

  val repo: ProductRepository = ProductRepository

  /**
   * A REST endpoint that gets all the products as JSON.
   */
  def getProducts = Action { implicit request =>
    val cid: Int = request.cookies.get("customerID") match {
      case Some(cookie) => cookie.value.toInt
      case None         => -1
    }
    val location: Location.Value = CostumerLocation.getLocation(cid);
    val products = Catalogue.getProducts(location)
    Ok(Json.toJson(products))
  }

  def confirmation = Action {
    Ok(views.html.confirmation())
  }

  def placeOrder = Action(parse.json) { implicit request =>
    //do something complex to place order...

    val cid = (request.body \ "customerID").asOpt[Long]
    val products = (request.body \ "products")

    val productsAsArray = products.asOpt[Array[Int]].getOrElse(Array.empty)
    val productsAsString = productsAsArray mkString(", ") 

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

