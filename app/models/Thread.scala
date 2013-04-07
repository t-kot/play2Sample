package models

import anorm._
import anorm.SqlParser._
import java.util.Date
import org.joda.time._
import play.api.db._
import play.api.Play._

//case classはclass定義において便利なメソッドを
//一緒に作ってくれるらしい
case class Thread(
  id: Long, title: String, createdAt: Date
) {
  //これはインスタンスメソッド
  def comments: List[Comment] = {
    DB.withConnection { implicit c =>
      SQL("""
        select * from comments
        where thread_id = {thread_id}
      """).on(
        //インスタンスの自分自身はthisで取れる
        'thread_id -> this.id
      ).as(Comment.simple *)
    }
  }
}

//これはシングルトンオブジェクト
object Thread {

  val simple = {
    get[Long]("id") ~
    get[String]("title") ~
    get[Date]("created_at") map {
      case id~title~createdAt =>
        Thread(id, title, createdAt)
    }
  }
  //クラスメソッド
  def all(): List[Thread] = {
    DB.withConnection { implicit c =>
      SQL(
        "select * from threads order by created_at desc"
      ).as(simple *)
    }
  }

  def find(id: Long): Thread = {
    DB.withConnection { implicit c =>
      SQL("""
        select * from threads
        where id = {id}
      """).on(
        'id -> id
      ).as(simple *).head
    }
  }

  def create(title: String) {
    DB.withConnection { implicit c =>
      SQL("""
        insert into threads (title, created_at)
        values ( {title}, {created_at})
      """).on(
        'title -> title,
        'created_at -> DateTime.now().toDate()
      ).executeUpdate()
    }
  }
}
