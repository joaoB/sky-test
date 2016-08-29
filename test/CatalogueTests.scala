import org.scalatest.FunSuite

import models.Location
import models.Product
import services.CostumerLocation
import services.Catalogue

class CatalogueTests extends FunSuite {

  val catalogue: Catalogue = Catalogue
  val liverpool = Location.liverpool
  val london = Location.london
  val everywhere = Location.everywhere

  test("Check the Liverpool products") {
    val liverpoolProducts = catalogue.getProducts(liverpool)
    assert(liverpoolProducts.length == 3)
    assert(liverpoolProducts exists (_.name.contains("Liverpool")))
  }

  test("Check the London products") {
    val londonProducts = catalogue.getProducts(london)
    assert(londonProducts.length == 4)
    assert(londonProducts exists (_.name.contains("Arsenal")))
    assert(londonProducts exists (_.name.contains("Chelsea")))
  }

  test("Check products available everywhere") {
    val liverpoolProducts = catalogue.getProducts(liverpool)
    val londonProducts = catalogue.getProducts(london)
    val everywhereProducts = catalogue.getProducts(everywhere)

    londonProducts.forall { p => everywhereProducts exists { x => x.name == p.name } }
    liverpoolProducts.forall { p => everywhereProducts exists { x => x.name == p.name } }

  }

}

