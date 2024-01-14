package com.soonyong.hong.batch.adpter.config.crawl

import com.soonyong.hong.batch.domain.crawl.filter.impl.*
import com.soonyong.hong.batch.domain.crawl.model.CrawlTarget
import com.soonyong.hong.batch.domain.provider.document.BasicHtmlDocumentProvider
import com.soonyong.hong.batch.domain.provider.text.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

private val crawlTargetMap: MutableMap<String, TextProvider> =
  HashMap<String, TextProvider>().apply {
    put(
      "gift-certificates", basicTextProvider(
        CrawlTarget(
          title = "gift-certificates",
          htmlDocumentProvider = BasicHtmlDocumentProvider(
            TextsToCombinedTextProvider(
              TextProvidersToTextsProvider(
                SimpleTextProvider("https://www.algumon.com/category/5/more/0?types=TYPE_ENDED&topSequence="),
                TextsToSelectedTextProvider(
                  textsProvider = RegexFilteredTextsProvider(
                    textProvider = TextsToSelectedTextProvider(
                      RegexFilteredTextsProvider(
                        textProvider = DocumentToTextProvider(BasicHtmlDocumentProvider("https://www.algumon.com/category/5")),
                        regex = Regex("var\\s+topSequence\\s*=\\s*\\d+")
                      ), index = 0
                    ), regex = Regex("\\d+")
                  ), index = 0
                )
              ), delimiter = ""
            )
          ),
          baseCssSelector = ".product-body",
          filter = CrawlFilterChain(
            CrawlFilterChain(
              delegate = SelectedTextFilterAdapter(
                cssSelector = ".deal-title .item-name", comparator = PatternMatchStringComparator(
                  pattern = Pattern.compile(".*(컬쳐랜드|컬처랜드|문화상품권|해피머니|북앤라이프).*")
                )
              ),
              delegateCondition = CrawlFilterChain.DelegateCondition.AND,
              next = SelectedTextFilterAdapter(
                cssSelector = ".product-body .product-price",
                comparator = PatternMatchStringComparator(
                  pattern = Pattern.compile("(4[0-5],?[0-9]{3}|46,?([01][0-9]{2}|2([0-4][0-9]|50)))\\s*원")
                )
              )
            ),
            delegateCondition = CrawlFilterChain.DelegateCondition.AND,
            next = SelectedTextFilterAdapter(
              cssSelector = ".header .label-time", comparator = PatternMatchStringComparator(
                pattern = Pattern.compile("^(\\D)*(방금|0?\\d분\\s*전|1[0-2]분\\s*전).*")
              )
            )
          ),
          targetTextSelector = CssSelectorTargetTextSelector(".deal-header-p .shop, .deal-title .item-name, .deal-price-info")
        )
      )
    )

    put(
      "ppomppu", basicTextProvider(
        CrawlTarget(
          title = "ppomppu",
          htmlDocumentProvider = BasicHtmlDocumentProvider("https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu&hotlist_flag=999"),
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
                ),
                delegateCondition = CrawlFilterChain.DelegateCondition.AND,
                next = SelectedTextFilterAdapter(
                  cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                    pattern = Pattern.compile("(1[5-9]|[2-9]\\d)\\s*-\\s*0")
                  )
                )
              ),
              delegateCondition = CrawlFilterChain.DelegateCondition.OR,
              next = SelectedTextFilterAdapter(
                cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                  pattern = Pattern.compile("([3-9]|\\d{2,})\\d\\s*-\\s*0")
                )
              )
            )
          ),
          targetTextSelector = CssSelectorTargetTextSelector(
            "td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font"
          )
        )
      )
    )
    put(
      "ppomppu_foreign", basicTextProvider(
        CrawlTarget(
          title = "ppomppu_foreign",
          htmlDocumentProvider = BasicHtmlDocumentProvider("https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4&hotlist_flag=999"),
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
                ),
                delegateCondition = CrawlFilterChain.DelegateCondition.AND,
                next = SelectedTextFilterAdapter(
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
                ),
                delegateCondition = CrawlFilterChain.DelegateCondition.AND,
                next = SelectedTextFilterAdapter(
                  cssSelector = "td:nth-child(5)", comparator = PatternMatchStringComparator(
                    pattern = Pattern.compile("\\d\\d\\s*-\\s*0")
                  )
                )
              )
            )
          ),
          targetTextSelector = CssSelectorTargetTextSelector("td:nth-child(3) > table > tbody > tr > td:nth-child(2) > div > a > font")
        )
      )
    )
    put(
      "quasarzone", basicTextProvider(
        CrawlTarget(
          title = "quasarzone",
          htmlDocumentProvider = BasicHtmlDocumentProvider("https://quasarzone.com/bbs/qb_saleinfo?popularity=Y"),
          baseCssSelector = "#frmSearch > div > div.list-board-wrap > div.market-type-list.market-info-type-list.relative > table > tbody > tr",
          filter = CrawlFilterChain(
            delegate = SelectedTextFilterAdapter(
              comparator = PatternMatchStringComparator(
                pattern = Pattern.compile("^((?!종료).)*$")
              )
            ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = CrawlFilterChain(
              delegate = SelectedTextFilterAdapter(
                cssSelector = "span.date", comparator = PatternMatchStringComparator(
                  pattern = Pattern.compile("\\d\\d:\\d\\d")
                ).and(
                  LocalTimeBaseStringComparator(
                    formatter = DateTimeFormatter.ofPattern("HH:mm"),
                    zoneId = ZoneId.of("Asia/Seoul"),
                    unit = ChronoUnit.HOURS,
                    interval = 8,
                    type = LocalTimeBaseStringComparator.Type.BEFORE
                  )
                )
              ), delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = CrawlFilterChain(
                delegate = SelectedTextFilterAdapter(
                  cssSelector = "td:nth-child(1) > span", comparator = PatternMatchStringComparator(
                    pattern = Pattern.compile("([3-9]|\\d\\d)\\d+")
                  )
                ),
                delegateCondition = CrawlFilterChain.DelegateCondition.AND,
                next = CrawlFilterChain(
                  delegate = SelectedTextFilterAdapter(
                    cssSelector = "span.date", comparator = PatternMatchStringComparator(
                      pattern = Pattern.compile("\\d\\d-\\d\\d")
                    ).and(
                      LocalDateBaseStringComparator(
                        formatter = DateTimeFormatterBuilder().appendPattern("MM-dd")
                          .parseDefaulting(
                            ChronoField.YEAR, LocalDate.now().minusDays(1).year.toLong()
                          ).toFormatter(),
                        zoneId = ZoneId.of("Asia/Seoul"),
                        unit = ChronoUnit.DAYS,
                        interval = 1,
                        type = LocalDateBaseStringComparator.Type.EQUALS
                      )
                    )
                  ),
                  delegateCondition = CrawlFilterChain.DelegateCondition.OR,
                  next = SelectedTextFilterAdapter(
                    cssSelector = "span.date", comparator = PatternMatchStringComparator(
                      pattern = Pattern.compile("\\d\\d:\\d\\d")
                    )
                  )
                )
              )
            )
          ),
          targetTextSelector = CompositeTargetTextSelector(
            delegate = CssSelectorTargetTextSelector(".market-info-list-cont > p > a > span"),
            delimiter = " | ",
            next = CssSelectorTargetTextSelector(
              "span.text-orange"
            )
          )
        )
      )
    )
  }

fun getCrawlTarget(title: String): TextProvider? {
  return crawlTargetMap[title]
}

private fun basicTextProvider(crawlTarget: CrawlTarget): TextProvider {
  return TextsToCombinedTextProvider(
    textsProvider = WebCrawler(crawlTarget)
  )
}