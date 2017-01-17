package com.knoldus

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait MainService {

  def route: Route =
    pathPrefix("demo") {
      pathEnd {
        get {
          complete {
            HttpResponse(OK, entity = "You are in demo")
          }
        }
      } ~
        path("test" / Segment) { id =>
          get {
            println("in test")
            complete {
              HttpResponse(OK, entity = s"You are in demo/test/$id")

            }
          }
        }
    }
}