package com.soonyong.hong.batch.notification.push.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.soonyong.hong.batch.notification.domain.NotificationService
import com.soonyong.hong.batch.notification.domain.model.NotificationMessage
import com.soonyong.hong.batch.notification.push.model.FireBaseAndroidPushNotificationBody
import com.soonyong.hong.batch.notification.push.model.FireBaseAndroidPushNotificationRequest
import mu.KotlinLogging
import org.apache.http.HttpException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Service
import java.nio.charset.Charset

private val log = KotlinLogging.logger {}
private val objectMapper = ObjectMapper().apply {
    registerModule(
        KotlinModule.Builder().configure(KotlinFeature.NullIsSameAsDefault, true).build()
    )
}

@Service
class FireBaseAndroidPushNotificationService : NotificationService {
    override fun notify(sendKey: String, message: NotificationMessage) {
        log.info { "notification requested with message $message" }
        HttpClients.createMinimal().use { httpClient ->
            val httpPost = HttpPost("https://fcm.googleapis.com/fcm/send").apply {
                addHeader("Content-Type", "application/json")
                addHeader("Authorization", "key=${sendKey}")
                entity = StringEntity(
                    objectMapper.writeValueAsString(
                        FireBaseAndroidPushNotificationRequest(
                            notification = FireBaseAndroidPushNotificationBody(
                                body = message.body, title = message.title
                            )
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