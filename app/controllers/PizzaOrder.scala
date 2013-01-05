package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object PizzaOrder extends Controller {

  val pizzaForm = Form(
    tuple(
      "pizzaType" -> nonEmptyText(maxLength = 128),
      "size" -> nonEmptyText(maxLength = 128),
      "address" -> nonEmptyText(maxLength = 128)
    )

  )

  def showOrderForm = Action {
    Ok(views.html.pizza.orderform(pizzaForm))
  }

  def orderPizza = Action { implicit request =>
    pizzaForm.bindFromRequest().fold(
      errors => BadRequest("Pizza request contained errors"), /* views.html.pizza.orderPizza(errors) */
      pizzaOrder => {
        Logger.info("Ordering pizza: " + pizzaOrder._1)

        // order business logic
        Redirect(routes.PizzaOrder.pizzaOrdered(pizzaOrder._1, pizzaOrder._2, pizzaOrder._3))
      }
    )
  }

  def pizzaOrdered(pizzaType: String, size: String, address: String) = Action {
    Ok(views.html.pizza.ordered(pizzaType, size, address))
  }

}
