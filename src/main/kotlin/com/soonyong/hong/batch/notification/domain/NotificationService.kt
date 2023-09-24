package com.soonyong.hong.batch.notification.domain

import com.soonyong.hong.batch.notification.domain.model.NotificationMessage

interface NotificationService {

  fun notify(sendKey: String, message: NotificationMessage) {}
}