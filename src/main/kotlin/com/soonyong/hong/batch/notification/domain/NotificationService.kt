package com.soonyong.hong.batch.notification.domain

interface NotificationService {

  fun notify(url: String, title: String, message: String) {}
}