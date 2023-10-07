package com.soonyong.hong.batch.adpter.config.crawl

import com.soonyong.hong.batch.adpter.job.crwal.WebCrawlTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CrawlJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val webCrawlTasklet: WebCrawlTasklet
) {

    @Bean
    fun crawlJob(): Job {
        return jobBuilderFactory["crawlJob"].start(webCrawlStep()).build()
    }

    @Bean
    fun webCrawlStep(): Step {
        return stepBuilderFactory["webCrawlStep"].tasklet(webCrawlTasklet).build()
    }
}