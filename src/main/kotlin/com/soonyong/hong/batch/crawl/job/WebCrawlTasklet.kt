package com.soonyong.hong.batch.crawl.job

import com.soonyong.hong.batch.crawl.service.CrawlService
import com.soonyong.hong.batch.notification.domain.NotificationServiceFactory
import com.soonyong.hong.batch.notification.domain.NotificationType
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

    @Value("#{jobParameters[url]}")
    private lateinit var url: String

    @Value("#{jobParameters[type]")
    private lateinit var type: NotificationType

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val result: List<String> = crawlService.getTexts(title)

        log.info("crawl result : {}", result)

        if (result.isNotEmpty() && this::url.isInitialized && this::type.isInitialized && StringUtils.hasText(
                url
            )
        ) {

            notificationServiceFactory.get(type).notify(url, title, result.joinToString("\n"))
        }
        return RepeatStatus.FINISHED
    }
}