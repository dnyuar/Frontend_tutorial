package utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

trait WireMockSetup {

  val wireMockHost = "localhost"
  val wireMockPort = 9000

  val wireMockConf: WireMockConfiguration = wireMockConf.port(wireMockPort)
  val wireMockServer: WireMockServer = new WireMockServer(wireMockConf)

}
