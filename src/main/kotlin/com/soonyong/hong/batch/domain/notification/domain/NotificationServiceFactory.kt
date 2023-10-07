package com.soonyong.hong.batch.domain.notification.domain

interface NotificationServiceFactory {
  fun get(type: NotificationType): NotificationService
}