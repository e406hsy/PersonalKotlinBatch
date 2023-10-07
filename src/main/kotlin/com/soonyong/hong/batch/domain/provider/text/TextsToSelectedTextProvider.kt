package com.soonyong.hong.batch.domain.provider.text

class TextsToSelectedTextProvider(
  private val textsProvider: TextsProvider, private val index: Int
) : TextProvider {
  override fun getText(): String = textsProvider.getTexts().getOrElse(index) { "" }

}