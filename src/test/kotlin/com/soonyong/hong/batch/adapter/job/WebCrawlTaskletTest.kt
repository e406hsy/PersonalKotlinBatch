package com.soonyong.hong.batch.adapter.job

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.JobRepositoryTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
class WebCrawlTaskletTest {

    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    private lateinit var jobRepositoryTestUtils: JobRepositoryTestUtils

    @Autowired
    private lateinit var crawlJob: Job

    @BeforeEach
    fun clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions()
    }

    @Test
    @Throws(Exception::class)
    fun testJob() {
        // given
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder.addString("title", "ppomppu")
            .toJobParameters()
        jobLauncherTestUtils.job = crawlJob
        // when
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        // then
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.exitStatus)
    }

}