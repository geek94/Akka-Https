package com.knoldus

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by rishabh on 19/10/16.
  */
class MainServiceTest extends WordSpec with Matchers with ScalatestRouteTest{

  val routes = new MainService {}.route

  "The service" should {

    "be able to return in get" in {
      Get("/demo/test/123456") ~> routes ~> check {
       responseAs[String].contains("You are in demo") shouldEqual true
      }
    }
    "be able to return in demo" in {
      Get("/demo/test/id") ~> routes ~> check {
        responseAs[String].contains("You are in demo/test/id") shouldEqual true
      }
    }

  }

}
