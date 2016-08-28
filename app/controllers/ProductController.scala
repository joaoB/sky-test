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

class ProductController @Inject() (val messagesApi: MessagesApi)(implicit ec: ExecutionContext) extends Controller with I18nSupport {

  val repo: ProductRepository = ProductRepository

  /**
   * A REST endpoint that gets all the products as JSON.
   */
  def getProducts = Action { implicit request =>
    val cid: Int = request.cookies.get("costumerID") match {
      case Some(cookie) => cookie.value.toInt
      case None         => -1
    }
    val location: Location.Value = CostumerLocation.getLocation(cid);
    val products = Catalogue.getProducts(location)
    Ok(Json.toJson(products))
  }

}

