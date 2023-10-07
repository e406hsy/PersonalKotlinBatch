package com.soonyong.hong.batch.adpter.config.spring

import com.soonyong.hong.batch.adpter.job.spring.RemoveSpringBatchHistoryTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RemoveSpringBatchHistoryJobConfig(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory,
  private val removeSpringBatchHistoryTasklet: RemoveSpringBatchHistoryTasklet
) {

  @Bean
  fun removeSpringBatchHistoryJob(): Job {
    return jobBuilderFactory["removeSpringBatchHistoryJob"].start(removeSpringBatchHistoryStep())
      .build()
  }

  @Bean
  fun removeSpringBatchHistoryStep(): Step {
    return stepBuilderFactory["removeSpringBatchHistoryStep"].tasklet(
      removeSpringBatchHistoryTasklet
    ).build()
  }
}