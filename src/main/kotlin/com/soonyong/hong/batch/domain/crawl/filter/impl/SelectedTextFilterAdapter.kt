package com.soonyong.hong.batch.domain.crawl.filter.impl

import com.soonyong.hong.batch.domain.crawl.filter.CrawlFilter
import mu.KotlinLogging
import org.jsoup.nodes.Element
import java.util.function.Predicate

private val log = KotlinLogging.logger {}
/**
 * CSS Selector를 하위 element를 선택하여 내부 텍스트를 검증하는 클래스
 *
 * @property cssSelector 하위 element 선택자. 없으면 현재 element를 사용한다.
 * @property comparator 선택된 텍스트를 검증하기 위한 검증자
 */
class SelectedTextFilterAdapter(
  private val comparator: (String) -> Boolean, private val cssSelector: String? = null
) : CrawlFilter {

  constructor(
    comparator: Predicate<String>, cssSelector: String? = null
  ) : this(comparator::test, cssSelector)

  /**
   * Element가 크롤링이 대상이 되는지 판단
   * @return 크롤링 대상일 경우 true 아니면 false
   */
  override fun isAllowed(value: Element): Boolean {
    val target = (this.cssSelector?.let { value.select(it).text() } ?: value.text()).trim()
    val result = comparator(target)
    log.debug("is allowed called with selected {} target {} and value {} and result is {}", cssSelector, target, value, result)
    return result
  }
}