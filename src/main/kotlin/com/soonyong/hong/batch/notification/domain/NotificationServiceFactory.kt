package com.soonyong.hong.batch.notification.domain

import com.soonyong.hong.batch.notification.dooray.service.DoorayNotificationService
import com.soonyong.hong.batch.notification.gotify.service.GotifyNotificationService
import com.soonyong.hong.batch.notification.push.service.FireBaseAndroidPushNotificationService
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class NotificationServiceFactory(private val applicationContext: ApplicationContext) {

  fun get(type: NotificationType): NotificationService {

    return when (type) {
      NotificationType.DOORAY -> applicationContext.getBean(DoorayNotificationService::class.java)
      NotificationType.GOTIFY -> applicationContext.getBean(GotifyNotificationService::class.java)
      NotificationType.FIREBASE -> applicationContext.getBean(FireBaseAndroidPushNotificationService::class.java)
    }
  }

}