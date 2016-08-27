package dal

import models.Product
import models.Category
import models.Location

object ProductRepository extends ProductRepository

class ProductRepository {
  val products = scala.collection.mutable.ListBuffer.empty[Product]
  products.+=(Product("Arsenal TV", Category.sports, Location.london))
  products.+=(Product("Chelsea TV", Category.sports, Location.london))
  products.+=(Product("Liverpool TV", Category.sports, Location.liverpool))
  products.+=(Product("Sky News", Category.news, Location.everywhere))
  products.+=(Product("Sky Sports News", Category.news, Location.everywhere))
  
  def list = products.toList
  
  
  def list(location: Location.Value){
    products.filter { prod =>  prod.location == location || prod.location == Location.everywhere}.toList
  }

}