package dal

import javax.inject.Singleton
import models.Person

object PersonRepository extends PersonRepository
class PersonRepository {
  private var persons = scala.collection.mutable.ListBuffer.empty[Person]
  private var ids = 0
  
  def create(name: String, age: Int) = {
    ids = ids + 1
    persons.+=(Person(ids, name, age))
  }
  
  /**
   * List all the people in the database.
   */
  def list = persons
}
