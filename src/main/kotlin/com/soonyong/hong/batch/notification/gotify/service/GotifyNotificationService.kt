package com.soonyong.hong.batch.notification.gotify.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.soonyong.hong.batch.notification.domain.NotificationService
import com.soonyong.hong.batch.notification.domain.model.NotificationMessage
import com.soonyong.hong.batch.notification.gotify.model.GotifyMessage
import mu.KotlinLogging
import org.apache.http.HttpException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.charset.Charset

private val log = KotlinLogging.logger {}
private val objectMapper = ObjectMapper().apply {
  registerModule(
    KotlinModule.Builder().configure(KotlinFeature.NullIsSameAsDefault, true).build()
  )
}


@Service
class GotifyNotificationService : NotificationService {

  @Throws(IOException::class, HttpException::class)
  override fun notify(url: String, message: NotificationMessage) {
    log.info { "notification requested with message $message" }
    HttpClients.createMinimal().use { httpClient ->
      val httpPost = HttpPost(url).apply {
        addHeader("Content-Type", "application/json")
        entity = StringEntity(
          objectMapper.writeValueAsString(
            GotifyMessage(
              title = message.title, message = message.body
            )
          ), Charset.forName("UTF-8")
        )
      }
      httpClient.execute(httpPost).use { httpResponse ->
        if (httpResponse.statusLine.statusCode != 200) {
          throw HttpException("http failed with response : $httpResponse")
        }
      }
    }
  }
}