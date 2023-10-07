package com.soonyong.hong.batch.domain.provider.text

class TextProvidersToTextsProvider(private vararg val textProviders: TextProvider) : TextsProvider {
  override fun getTexts(): List<String> = textProviders.map { it.getText() }

}