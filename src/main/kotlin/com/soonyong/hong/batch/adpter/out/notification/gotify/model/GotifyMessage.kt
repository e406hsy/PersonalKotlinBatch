package com.soonyong.hong.batch.adpter.out.notification.gotify.model

data class GotifyMessage(
  val title: String, val message: String, val priority: Int = 0
)