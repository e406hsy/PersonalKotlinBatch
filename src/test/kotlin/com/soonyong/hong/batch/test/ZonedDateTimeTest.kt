package com.soonyong.hong.batch.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * @author soonyong.hong
 */
class ZonedDateTimeTest : StringSpec({

  "test" {

    val now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/London"))
    val postCreatedTime = LocalTime.of(10, 12)
    val postCreatedDateTime = now.withHour(postCreatedTime.hour).withMinute(postCreatedTime.minute)

    now.hour shouldBe ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul")).minusHours(8).hour
    postCreatedDateTime.hour shouldBe 10
    postCreatedDateTime.minute shouldBe 12
  }
})