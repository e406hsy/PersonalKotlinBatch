package com.soonyong.hong.batch.notification.push.model

data class FireBaseAndroidPushNotificationRequest(
    val to: String = "/topics/all", val notification: FireBaseAndroidPushNotificationBody
)
