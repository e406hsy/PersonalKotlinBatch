package com.soonyong.hong.batch.notification

import com.soonyong.hong.batch.notification.model.NotificationMessage

interface NotificationService {
    fun notify(sendKey: String, message: NotificationMessage)
}