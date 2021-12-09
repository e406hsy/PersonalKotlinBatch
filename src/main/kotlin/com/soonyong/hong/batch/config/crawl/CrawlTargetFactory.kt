package com.soonyong.hong.batch.config.crawl

import com.soonyong.hong.batch.crawl.model.CrawlTarget
import org.jsoup.nodes.Element

class CrawlTargetFactory

private val crawlTargetMap: MutableMap<String, CrawlTarget> = HashMap<String, CrawlTarget>().apply {
    put(
        "ppomppu",
        CrawlTarget(
            title = "ppomppu",
            url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu&hotlist_flag=999",
            baseCssSelector = "#revolution_main_table > tbody > tr[align=\"center\"]",
            filter = { element: Element -> true },
//                CrawlFilterChain.builder()
//                    .delegate(
//                        SelectedTextFilterAdapter.builder().cssSelector("td:nth-child(4) > nobr")
//                            .delegate(value -> PatternMatchStringComparater.builder()
//    .pattern(Pattern.compile("\\d\\d:\\d\\d:\\d\\d")).build()
//    .and(
//        LocalTimeBaseStringComparater.builder()
//            .formatter(DateTimeFormatter.ofPattern("HH:mm:ss")).interval(8)
//            .zoneId(ZoneId.of("Asia/Seoul")).unit(ChronoUnit.HOURS)
//            .type(Type.BEFORE).build()
//    )
//    .test(value))
//    .build())
//    .delegateCondition(DelegateCondition.AND)
//    .next(
//        SelectedTextFilterAdapter.builder().cssSelector("td:nth-child(5)")
//            .delegate(
//                PatternMatchStringComparater.builder()
//                    .pattern(Pattern.compile("(1[5-9]|[2-9]\\d)\\s*-\\s*0")).build()
//            )
//            .build()
//    )
//    .build())
            targetCssSelector = "td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font"
        )
    )
//    )
//    add(
//        CrawlTarget.builder().title("ppomppu_foreign")
//            .url("https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4&hotlist_flag=999")
//            .baseCssSeletor("#revolution_main_table > tbody > tr[align=\"center\"]")
//            .filter(
//                CrawlFilterChain.builder()
//                    .delegate(
//                        SelectedTextFilterAdapter.builder().cssSelector("td:nth-child(4) > nobr")
//                            .delegate(value -> PatternMatchStringComparater.builder()
//    .pattern(Pattern.compile("\\d\\d:\\d\\d:\\d\\d")).build()
//    .and(
//        LocalTimeBaseStringComparater.builder()
//            .formatter(DateTimeFormatter.ofPattern("HH:mm:ss")).interval(8)
//            .zoneId(ZoneId.of("Asia/Seoul")).unit(ChronoUnit.HOURS)
//            .type(Type.BEFORE).build()
//    )
//    .test(value))
//    .build())
//    .delegateCondition(DelegateCondition.AND)
//    .next(
//        SelectedTextFilterAdapter.builder().cssSelector("td:nth-child(5)")
//            .delegate(
//                PatternMatchStringComparater.builder()
//                    .pattern(Pattern.compile("\\d\\d\\s*-\\s*0")).build()
//            )
//            .build()
//    )
//    .build())
//    .targetCssSeletor("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font").build());
}

fun get(title: String): CrawlTarget? {
    return crawlTargetMap[title]
}
