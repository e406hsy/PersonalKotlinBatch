package com.soonyong.hong.batch.provider.text

class SimpleTextProvider(private val text: String) : TextProvider {
  override fun getText() = text
}