package dal

import models.Product
import models.Category
import models.Location

object ProductRepository extends ProductRepository

class ProductRepository {
  val products = scala.collection.mutable.ListBuffer.empty[Product]
  products.+=(Product(1, "Arsenal TV", Category.sports, Location.london))
  products.+=(Product(2, "Chelsea TV", Category.sports, Location.london))
  products.+=(Product(3, "Liverpool TV", Category.sports, Location.liverpool))
  products.+=(Product(4, "Sky News", Category.news, Location.everywhere))
  products.+=(Product(5, "Sky Sports News", Category.news, Location.everywhere))

  def list = products.toList

}