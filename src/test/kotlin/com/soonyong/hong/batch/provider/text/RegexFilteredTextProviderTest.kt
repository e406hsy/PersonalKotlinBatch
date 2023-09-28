package com.soonyong.hong.batch.provider.text

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class RegexFilteredTextProviderTest : StringSpec({

  "기본적인 Regex 결과 확인" {
    val texts =
      RegexFilteredTextsProvider(SimpleTextProvider("abcd123efd456"), Regex("\\d")).getTexts()
    assertSoftly(texts) {
      it[0] shouldBe "1"
      it[1] shouldBe "2"
      it[2] shouldBe "3"
      it[3] shouldBe "4"
      it[4] shouldBe "5"
      it[5] shouldBe "6"
    }
  }

  "복잡한 Regex 결과 확인" {
    val texts = RegexFilteredTextsProvider(
      SimpleTextProvider(
        "<html>" + "<body>" + "<script>\nval key = \"value\"</script>" + "</body>" + "</html>"
      ), Regex("val\\s+key\\s*=\\s*['\"][^'\"]+['\"]")
    ).getTexts()
    assertSoftly(texts) {
      it.size shouldBe 1
      it[0] shouldBe "val key = \"value\""
    }
  }

  "복잡한 Regex 결과 확인2" {
    val texts = RegexFilteredTextsProvider(
      SimpleTextProvider(
        "<html>" + "<body>" + "<script>\nval key = 1232124134;</script>" + "</body>" + "</html>"
      ), Regex("val\\s+key\\s*=\\s*\\d+")
    ).getTexts()
    assertSoftly(texts) {
      it.size shouldBe 1
      it[0] shouldBe "val key = 1232124134"
    }
  }
})

