package com.soonyong.hong.batch.domain.provider.text

class SimpleTextProvider(private val text: String) : TextProvider {
  override fun getText() = text
}