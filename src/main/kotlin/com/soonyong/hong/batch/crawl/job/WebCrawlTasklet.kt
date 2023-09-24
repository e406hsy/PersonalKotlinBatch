package com.soonyong.hong.batch.crawl.job

import com.soonyong.hong.batch.crawl.service.CrawlService
import com.soonyong.hong.batch.notification.domain.NotificationServiceFactory
import com.soonyong.hong.batch.notification.domain.NotificationType
import com.soonyong.hong.batch.notification.domain.model.NotificationMessage
import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

private val log = KotlinLogging.logger {}

@StepScope
@Component
class WebCrawlTasklet(
  private val crawlService: CrawlService,
  private val notificationServiceFactory: NotificationServiceFactory
) : Tasklet {

  @Value("#{jobParameters[title]}")
  private lateinit var title: String

  @Value("#{jobParameters[key]}")
  private lateinit var key: String

  @Value("#{jobParameters[type]")
  private lateinit var type: NotificationType

  override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
    val results: List<String> = crawlService.getTexts(title)

    log.info("crawl result : {}", results)

    if (results.isNotEmpty() && this::key.isInitialized && this::type.isInitialized && StringUtils.hasText(
        key
      )
    ) {
      notificationServiceFactory.get(type)
        .notify(key, NotificationMessage(title, results.joinToString("\n")))
    }
    return RepeatStatus.FINISHED
  }
}