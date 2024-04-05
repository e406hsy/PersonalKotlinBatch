package com.soonyong.hong.batch.adpter.out.notification

import com.soonyong.hong.batch.adpter.out.notification.dooray.service.DoorayNotificationService
import com.soonyong.hong.batch.adpter.out.notification.gotify.service.GotifyNotificationService
import com.soonyong.hong.batch.adpter.out.notification.push.service.FireBaseAndroidPushNotificationService
import com.soonyong.hong.batch.domain.notification.domain.NotificationService
import com.soonyong.hong.batch.domain.notification.domain.NotificationServiceFactory
import com.soonyong.hong.batch.domain.notification.domain.NotificationType
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class NotificationServiceFactoryImpl(private val applicationContext: ApplicationContext) :
  NotificationServiceFactory {

  override fun get(type: NotificationType): NotificationService {

    return when (type) {
      NotificationType.DOORAY -> applicationContext.getBean(DoorayNotificationService::class.java)
      NotificationType.GOTIFY -> applicationContext.getBean(GotifyNotificationService::class.java)
      NotificationType.FIREBASE -> applicationContext.getBean(FireBaseAndroidPushNotificationService::class.java)
      NotificationType.NONE -> object : NotificationService {}
    }
  }

}