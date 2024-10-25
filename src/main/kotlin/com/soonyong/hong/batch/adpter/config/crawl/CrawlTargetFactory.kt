package com.soonyong.hong.batch.adpter.config.crawl

import com.soonyong.hong.batch.domain.crawl.filter.impl.*
import com.soonyong.hong.batch.domain.crawl.model.CrawlTarget
import com.soonyong.hong.batch.domain.provider.document.BasicHtmlDocumentProvider
import com.soonyong.hong.batch.domain.provider.text.*
import mu.KotlinLogging
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern

private val log = KotlinLogging.logger {}
private val crawlTargetMap: MutableMap<String, TextProvider> = HashMap<String, TextProvider>().apply {
  put(
    "gift-certificates", basicTextProvider(
      CrawlTarget(
        title = "gift-certificates", htmlDocumentProvider = BasicHtmlDocumentProvider(
          TextsToCombinedTextProvider(
            TextProvidersToTextsProvider(
              SimpleTextProvider("https://www.algumon.com/category/5/more/0?types=TYPE_ENDED&topSequence="), TextsToSelectedTextProvider(
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
        ), baseCssSelector = ".product-body", filter = CrawlFilterChain(
          CrawlFilterChain(
            delegate = SelectedTextFilterAdapter(
              cssSelector = ".deal-title .item-name", comparator = PatternMatchStringComparator(
                pattern = Pattern.compile(".*(컬쳐랜드|컬처랜드|문화상품권|해피머니|북앤라이프).*")
              )
            ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = SelectedTextFilterAdapter(
              cssSelector = ".product-body .product-price", comparator = PatternMatchStringComparator(
                pattern = Pattern.compile("(4[0-5],?[0-9]{3}|9,200|46,?([0-4][0-9]{2}|5([0-4][0-9]|50)))\\s*원")
              )
            )
          ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = SelectedTextFilterAdapter(cssSelector = "small.deal-price-meta-info",
            comparator = PatternMatchStringComparator(
              pattern = Pattern.compile("^(\\D)*(방금|0?\\d분\\s*전|1[0-2]분\\s*전).*")
            )
          )
        ), targetTextSelector = CssSelectorTargetTextSelector(".deal-header-p .shop, .deal-title .item-name, .deal-price-info")
      )
    )
  )
  put("naver-points", SimpleTextProvider {
    BasicHtmlDocumentProvider("https://bbs.ruliweb.com/news/board/1020/").getDocument().select("tr.table_body.blocktarget").asSequence()
      .filter { element: Element ->
        log.debug { element }
        val subject = element.select("td.subject").text().also { log.debug { it } }
        subject.matches(Regex(".*(\\(|\\[)\\s*네이버\\s*페이\\s*(\\)|\\]).*"))
      }.filter { element: Element ->
        val timeText = element.select("td.time").text()
        log.debug { timeText }
        if (!timeText.matches(Regex("\\d{1,2}:\\d{1,2}"))) {
          return@filter false
        }
        val now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul"))
        val postCreatedTime = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm"))
        val postCreatedDateTime = now.withHour(postCreatedTime.hour).withMinute(postCreatedTime.minute)

        now.minusMinutes(10).isBefore(postCreatedDateTime) || (now.hour == 0 && now.minute < 10)
      }.map { element: Element ->
        element.select("td.subject a").first()?.attr("href")?.let {
          BasicHtmlDocumentProvider(it)
        }?.getDocument()?.select("div.view_content article")?.text() ?: ""
      }.toSet().filter {
        it.matches(Regex(".*https://.*"))
      }.joinToString(separator = "\n----------------------------\n")
  })

  put("ppomppu", SimpleTextProvider {
    BasicHtmlDocumentProvider("https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu&hotlist_flag=999").getDocument()
      .select("#revolution_main_table > tbody > tr[align=\"center\"]")
      .asSequence()
      .filter {
        it.html().also { log.debug { it } }.contains("/zboard/skin/DQ_Revolution_BBS_New1/end_icon.PNG").not()
      }
      .filter {
        val upAndDowns = it.select(".baseList-rec").text().also { log.debug { it } }
        if (upAndDowns.matches(Regex("([3-9]|\\d{2,})\\d\\s*-\\s*0"))) {
          return@filter true
        }
        if (!upAndDowns.matches(Regex("(1[5-9]|[2-9]\\d)\\s*-\\s*0"))) {
          return@filter false
        }

        val timeText = it.select("time.baseList-time").also { log.debug { it } }.text()
        if (!timeText.matches(Regex("\\d{1,2}:\\d{1,2}:\\d{1,2}"))) {
          return@filter false
        }
        val now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul"))
        val postCreatedTime = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm:ss"))
        val postCreatedDateTime = now.withHour(postCreatedTime.hour).withMinute(postCreatedTime.minute)

        now.minusHours(8).isBefore(postCreatedDateTime)
      }
      .filter { it.select(".baseList-numb").text().matches(Regex("\\d+")) }
      .map { it.select(".baseList-title span").text() }
      .joinToString(separator = "\n")
  })

  put("ppomppu_foreign", basicTextProvider(CrawlTarget(title = "ppomppu_foreign", htmlDocumentProvider = BasicHtmlDocumentProvider(
    "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4&hotlist_flag=999"),
                                                       baseCssSelector = "#revolution_main_table > tbody > tr[align=\"center\"]",
                                                       filter = CrawlFilterChain(
                                                         delegate = SelectedTextFilterAdapter(comparator = PatternMatchStringComparator(
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
              ), delegateCondition = CrawlFilterChain.DelegateCondition.AND, next = CrawlFilterChain(
                delegate = SelectedTextFilterAdapter(
                  cssSelector = "span.date", comparator = PatternMatchStringComparator(
                    pattern = Pattern.compile("\\d\\d-\\d\\d")
                  ).and(
                    LocalDateBaseStringComparator(
                      formatter = DateTimeFormatterBuilder().appendPattern("MM-dd").parseDefaulting(
                        ChronoField.YEAR, LocalDate.now().minusDays(1).year.toLong()
                      ).toFormatter(),
                      zoneId = ZoneId.of("Asia/Seoul"),
                      unit = ChronoUnit.DAYS,
                      interval = 1,
                      type = LocalDateBaseStringComparator.Type.EQUALS
                    )
                  )
                ), delegateCondition = CrawlFilterChain.DelegateCondition.OR, next = SelectedTextFilterAdapter(
                  cssSelector = "span.date", comparator = PatternMatchStringComparator(
                    pattern = Pattern.compile("\\d\\d:\\d\\d")
                  )
                )
              )
            )
          )
        ),
        targetTextSelector = CompositeTargetTextSelector(
          delegate = CssSelectorTargetTextSelector(".market-info-list-cont > p > a > span"), delimiter = " | ", next = CssSelectorTargetTextSelector(
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