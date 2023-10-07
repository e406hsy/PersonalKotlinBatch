package com.soonyong.hong.batch.domain.crawl.filter.impl

import com.soonyong.hong.batch.domain.crawl.filter.CrawlFilter
import mu.KotlinLogging
import org.jsoup.nodes.Element
import java.util.function.BiFunction
import java.util.function.Predicate

private val log = KotlinLogging.logger {}

class CrawlFilterChain(
    private val delegate: CrawlFilter,
    private val next: CrawlFilter,
    private val delegateCondition: DelegateCondition,
) : CrawlFilter {

    override fun isAllowed(value: Element): Boolean {
        log.debug("is allowed called with value {}", value)
        val result = delegateCondition.getPredicate(delegate, next).test(value)
        log.debug("and result : {}", result)
        return result
    }

    enum class DelegateCondition(private val predicateGenerator: BiFunction<Predicate<Element>, Predicate<Element>, Predicate<Element>>) {
        AND({ pre1, pre2 -> pre1.and(pre2) }), OR({ pre1, pre2 -> pre1.or(pre2) });

        fun getPredicate(delegate: CrawlFilter, next: CrawlFilter): Predicate<Element> {
            return predicateGenerator.apply(Predicate { input: Element -> delegate.isAllowed(input) },
                Predicate { input: Element -> next.isAllowed(input) })
        }
    }
}