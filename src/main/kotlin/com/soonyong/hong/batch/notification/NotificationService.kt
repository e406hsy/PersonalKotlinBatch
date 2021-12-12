package com.soonyong.hong.batch.notification

interface NotificationService {
    fun notify(sendKey: String, message: String)
}