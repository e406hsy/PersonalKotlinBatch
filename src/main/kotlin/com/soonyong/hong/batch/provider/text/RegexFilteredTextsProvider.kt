package com.soonyong.hong.batch.provider.text

class RegexFilteredTextsProvider(private val textProvider: TextProvider, private val regex: Regex) :
  TextsProvider {
  override fun getTexts(): List<String> {
    return regex.findAll(textProvider.getText()).map { it.value }.toList()
  }
}