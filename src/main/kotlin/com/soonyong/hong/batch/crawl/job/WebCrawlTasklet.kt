package com.soonyong.hong.batch.crawl.job

import com.soonyong.hong.batch.crawl.service.CrawlService
import com.soonyong.hong.batch.notification.NotificationService
import com.soonyong.hong.batch.notification.model.NotificationMessage
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
    private val doorayNotificationService: NotificationService,
    private val fireBaseAndroidPushNotificationService: NotificationService
) : Tasklet {

    @Value("#{jobParameters[title]}")
    private lateinit var title: String

    @Value("#{jobParameters[hookUrl]}")
    private lateinit var hookUrl: String

    @Value("#{jobParameters[fireBaseAuthorizationKey]}")
    private lateinit var fireBaseAuthorizationKey: String

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val results: List<String> = crawlService.getTexts(title)

        log.info("crawl result : {}", results)

        if (results.isNotEmpty()) {
            if (this::hookUrl.isInitialized && StringUtils.hasText(hookUrl)) {
                doorayNotificationService.notify(
                    hookUrl, NotificationMessage(title = this.title, body = results.joinToString("\n"))
                )
            }
            if (this::fireBaseAuthorizationKey.isInitialized && StringUtils.hasText(fireBaseAuthorizationKey)) {
                results.forEach { result ->
                    fireBaseAndroidPushNotificationService.notify(
                        fireBaseAuthorizationKey, NotificationMessage(title = this.title, body = result)
                    )
                }
            }
        }
        return RepeatStatus.FINISHED
    }
}