package com.soonyong.hong.batch.notification.dooray.model

data class DoorayNotificationRequest(
    val botName: String = "/topics/all", val text: String
)