package com.soonyong.hong.batch.domain.notification.domain

import com.soonyong.hong.batch.domain.notification.domain.model.NotificationMessage

interface NotificationService {

  fun notify(sendKey: String, message: NotificationMessage) {}
}