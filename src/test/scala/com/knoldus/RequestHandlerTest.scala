package com.knoldus

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by rishabh on 19/10/16.
  */
class RequestHandlerTest extends WordSpec with Matchers with ScalatestRouteTest{

  val routes = new RequestHandler {}.route

  "The service" should {

    "be able to return in demo" in {
      Get("/demo") ~> routes ~> check {
       responseAs[String].equals("You are in demo") shouldEqual true
      }
    }

    "be able to return in test" in {
      Get("/demo/test/id") ~> routes ~> check {
        responseAs[String].equals("You are in demo/test/id") shouldEqual true
      }
    }

  }

}
