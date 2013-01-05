package controllers

import play.api.{Logger, GlobalSettings}
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {
  /**
   * 404: When action method not found for the request OR no binding found for a POST.
   * @param request
   * @return
   */
  override def onHandlerNotFound(request: RequestHeader) = {
    if (request.path != "/favicon.ico") {
      Logger.info("Not found: " + request.path)
    }

    NotFound(views.html.errors.notFoundPage(request.path))
  }

  /**
   * 400: When URL parameters aren't valid for the route matched.
   * @param request
   * @param error
   * @return
   */
  override def onBadRequest(request: RequestHeader, error: String) = {
    Logger.info("Bad request: " + request.toString() + "; error = " + error)
    BadRequest("Bad Request: " + error)
  }

  /**
   * 500: Programmer error
   */
  override def onError(request: RequestHeader, ex: Throwable) = {
    Logger.info("Server error: " + request.path, ex)
    InternalServerError(views.html.errors.errorPage(ex))
  }
}
