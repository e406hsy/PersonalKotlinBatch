package com.soonyong.hong.batch.domain.provider.text

class SimpleTextProvider(private val provider: () -> String) : TextProvider {

  constructor(text: String) : this({ text })

  override fun getText() = provider()
}