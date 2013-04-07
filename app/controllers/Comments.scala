package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.data._
import play.api.data.Forms._

object Comments extends Controller {

  val commentForm = Form(
    "body" -> nonEmptyText(maxLength = 1000)
  )

  def create(thread_id: Long) = Action { implicit request =>
    commentForm.bindFromRequest.fold(
      errors => BadRequest(
        views.html.threads.show(errors, Thread.find(thread_id))
      ),
      body => {
        Comment.createWithThread(thread_id, body)
        Redirect(routes.Threads.show(thread_id))
      }
    )
  }
}
