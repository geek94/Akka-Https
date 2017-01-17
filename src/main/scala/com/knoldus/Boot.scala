package com.knoldus

import java.io.InputStream
import java.security.{KeyStore, SecureRandom}
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http, HttpsConnectionContext}
import akka.stream.ActorMaterializer
import com.typesafe.sslconfig.akka.AkkaSSLConfig

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

/**
  * Created by rishabh on 9/10/16.
  */
object Boot extends App {

  implicit val actorSystem: akka.actor.ActorSystem = ActorSystem()
  val sslConfig = AkkaSSLConfig(actorSystem)

  implicit val flowMaterializer = ActorMaterializer()


  val ks: KeyStore = KeyStore.getInstance("PKCS12")
  val keystore: InputStream = getClass.getClassLoader.getResourceAsStream("keystore.pkcs12")

  require(keystore != null, "Keystore required!")

  val password: Array[Char] = "akka-https".toCharArray // do not store passwords in code, read them from somewhere safe!
  ks.load(keystore, password)

  val keyManagerFactory: KeyManagerFactory = KeyManagerFactory.getInstance("SunX509")
  keyManagerFactory.init(ks, password)

  val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("SunX509")
  tmf.init(ks)

  val sslContext: SSLContext = SSLContext.getInstance("TLS")
  sslContext.init(keyManagerFactory.getKeyManagers, tmf.getTrustManagers, new SecureRandom)
  val https: HttpsConnectionContext = ConnectionContext.https(sslContext)
  val route = new MainService {}.route

  val httpsBinding = Http().bindAndHandle(route, "127.0.0.1", 4400, connectionContext = https)

  println(s"Server is now online at http://8080\nPress RETURN to stop...")

  StdIn.readLine()
  httpsBinding.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
}
