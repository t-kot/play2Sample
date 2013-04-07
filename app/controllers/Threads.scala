package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.data._
import play.api.data.Forms._


object Threads extends Controller {

  val threadForm = Form(
    "title" -> nonEmptyText(maxLength = 100)
  )

  val commentForm = Form(
    "body" -> nonEmptyText(maxLength = 1000)
  )

  def index = Action {
    Ok(views.html.threads.index(threadForm, Thread.all()))
  }

  def show(id: Long) = Action {
    Ok(views.html.threads.show(commentForm, Thread.find(id)))
  }

  def create = Action { implicit request =>
    threadForm.bindFromRequest.fold(
      errors => BadRequest(
        views.html.threads.index(errors, Thread.all())
      ),
      title => {
        Thread.create(title)
        Redirect(routes.Threads.index)
      }
    )
  }

}
