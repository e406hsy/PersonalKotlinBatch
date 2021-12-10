package com.soonyong.hong.batch.config.crawl

import com.soonyong.hong.batch.crawl.filter.impl.*
import com.soonyong.hong.batch.crawl.model.CrawlTarget
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

private val crawlTargetMap: MutableMap<String, CrawlTarget> = HashMap<String, CrawlTarget>().apply {
    put(
        "ppomppu", CrawlTarget(
            title = "ppomppu",
            url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu&hotlist_flag=999",
            baseCssSelector = "#revolution_main_table > tbody > tr[align=\"center\"]",
            filter = CrawlFilterChain(
                delegate = SelectedTextFilterAdapter(
                    comparator = PatternMatchStringComparator(
                        pattern = Pattern.compile("^((?!/zboard/skin/DQ_Revolution_BBS_New1/end_icon\\.PNG).)*$")
                    )
                ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = CrawlFilterChain(
                    delegate = CrawlFilterChain(
                        delegate = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(4) > nobr", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d")
                            ).and(
                                LocalTimeBaseStringComparator(
                                    formatter = DateTimeFormatter.ofPattern("HH:mm:ss"),
                                    zoneId = ZoneId.of("Asia/Seoul"),
                                    unit = ChronoUnit.HOURS,
                                    interval = 8,
                                    type = LocalTimeBaseStringComparator.Type.BEFORE
                                )
                            )
                        ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("(1[5-9]|[2-9]\\d)\\s*-\\s*0")
                            )
                        )
                    ), delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = SelectedTextFilterAdapter(
                        cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                            pattern = Pattern.compile("([3-9]|\\d{2,})\\d\\s*-\\s*0")
                        )
                    )
                )
            ),
            targetCssSelector = "td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font"
        )
    )
    put(
        "ppomppu_foreign", CrawlTarget(
            title = "ppomppu_foreign",
            url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4&hotlist_flag=999",
            baseCssSelector = "#revolution_main_table > tbody > tr[align=\"center\"]",
            filter = CrawlFilterChain(
                delegate = SelectedTextFilterAdapter(
                    comparator = PatternMatchStringComparator(
                        pattern = Pattern.compile("^((?!/zboard/skin/DQ_Revolution_BBS_New1/end_icon\\.PNG).)*$")
                    )
                ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = CrawlFilterChain(
                    delegate = CrawlFilterChain(
                        delegate = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(4) > nobr", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d")
                            )
                        ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("\\d\\d\\s*-\\s*0")
                            )
                        )
                    ), delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = CrawlFilterChain(
                        delegate = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(4) > nobr", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("\\d\\d/\\d\\d/\\d\\d")
                            ).and(
                                LocalDateBaseStringComparator(
                                    formatter = DateTimeFormatter.ofPattern("yy/MM/dd"),
                                    zoneId = ZoneId.of("Asia/Seoul"),
                                    unit = ChronoUnit.DAYS,
                                    interval = 1,
                                    type = LocalDateBaseStringComparator.Type.BEFORE
                                )
                            )
                        ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = SelectedTextFilterAdapter(
                            cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                                pattern = Pattern.compile("\\d\\d\\s*-\\s*0")
                            )
                        )
                    )
                )
            ),
            targetCssSelector = "td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font"
        )
    )
}

fun getCrawlTarget(title: String): CrawlTarget? {
    return crawlTargetMap[title]
}
