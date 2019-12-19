package io.github.koustavtub

import java.net.ServerSocket

import akka.actor.{ActorRef, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import me.snov.sns.actor._
import me.snov.sns.api._
import me.snov.sns.service.FileDbService
import me.snov.sns.util.ToStrict

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.Properties

object MockSNSServer extends ToStrict {
  implicit val system: ActorSystem = ActorSystem("sns")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val logger: LoggingAdapter = Logging(system, getClass)
  implicit val timeout: Timeout = new Timeout(1.second)

  val config: Config = ConfigFactory.load()
  val dbService = new FileDbService(Properties.envOrElse("DB_PATH", findDbPath))

  val dbActor: ActorRef = system.actorOf(DbActor.props(dbService), name = "DbActor")
  val homeActor: ActorRef = system.actorOf(HomeActor.props, name = "HomeActor")
  val subscribeActor: ActorRef = system.actorOf(SubscribeActor.props(dbActor), name = "SubscribeActor")
  val publishActor: ActorRef = system.actorOf(PublishActor.props(subscribeActor), name = "PublishActor")

  val routes: Route =
    toStrict {
      TopicApi.route(subscribeActor) ~
        SubscribeApi.route(subscribeActor) ~
        PublishApi.route(publishActor) ~
        HealthCheckApi.route ~
        HomeApi.route(homeActor)
    }

  def start(): Int = {
    start(findRandomOpenPortOnAllLocalInterfaces);
  }

  def start(portNumber: Int): Int = {
    val serverPort = portNumber
    Http().bindAndHandle(
      handler = logRequestResult("akka-http-sns")(routes),
      interface = Properties.envOrElse("HTTP_INTERFACE", config.getString("http.interface")),
      port = serverPort
    )
    serverPort
  }
private def findDbPath:String={
  val dbPath = config.getString("db.path")
   Option(dbPath) match {
     case null=> findDbPath
     case _=> dbPath
   }
}
  private def findRandomOpenPortOnAllLocalInterfaces: Int = {
    val configuredPort = config.getInt("http.port")
    if (configuredPort >0) {
      configuredPort
    }
    else {
      val socket = new ServerSocket(0)
      try
        new Some[Int](socket.getLocalPort).get

      finally {
        if (socket != null) socket.close()
      }
    }
  }
}
