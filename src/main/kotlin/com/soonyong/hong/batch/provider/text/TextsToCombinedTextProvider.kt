package com.soonyong.hong.batch.provider.text

class TextsToCombinedTextProvider(
  private val textsProvider: TextsProvider, private val delimiter: String = "\n"
) : TextProvider {
  override fun getText(): String = textsProvider.getTexts().joinToString(delimiter)

}