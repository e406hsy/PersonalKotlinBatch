package com.soonyong.hong.batch.notification.push.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FireBaseAndroidPushNotificationBody(val body: String, val title: String? = null)