package services

import models.Location
import dal.ProductRepository

object Catalogue extends Catalogue
class Catalogue {

  def getProducts(loc: Location.Value) = {
    val everywhere = Location.everywhere;
    ProductRepository.products filter (p => p.location == loc || p.location == everywhere) toList
  }
}