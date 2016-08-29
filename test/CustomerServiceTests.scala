
import org.scalatest.FunSuite

import models.Location
import services.CostumerLocation

class CustomerServiceTests extends FunSuite {

  val customerLocation: CostumerLocation = CostumerLocation

  test("client location depends on id") {
    //even return london
    //odd return liverpool
    assert(customerLocation.getLocation(0).get == Location.london)
    assert(customerLocation.getLocation(1).get == Location.liverpool)
  }

}

