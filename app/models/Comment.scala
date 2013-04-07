package models

import anorm._
import anorm.SqlParser._
import java.util.Date
import org.joda.time._
import play.api.db._
import play.api.Play._

case class Comment(
  id: Long, body: String, createdAt: Date, thread_id: Long
)

object Comment {

  val simple = {
    get[Long]("id") ~
    get[String]("body") ~
    get[Date]("created_at") ~
    get[Long]("thread_id") map {
      case id~body~createdAt~threadId =>
        Comment(id, body, createdAt, threadId)
    }
  }

  def createWithThread(thread_id: Long, body: String) {
    DB.withConnection { implicit c =>
      SQL("""
        insert into comments (body, created_at, thread_id)
        values ( {body}, {created_at}, {thread_id})
      """).on(
        'body -> body,
        'created_at -> DateTime.now().toDate(),
        'thread_id -> thread_id
      ).executeUpdate()
    }
  }

  def of(thread_id: Long): List[Comment] =  {
    DB.withConnection { implicit c =>
      SQL("""
        select * from comments
        where thread_id = {thread_id}
      """).on(
        'thread_id -> thread_id
      ).as(simple *)
    }
  }
}
