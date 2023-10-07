package com.soonyong.hong.batch.adpter.job.spring

import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.repository.dao.AbstractJdbcBatchMetadataDao
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDate

private val LOG = KotlinLogging.logger {}


@Component
class RemoveSpringBatchHistoryTasklet ( private val jdbcTemplate: JdbcTemplate ) : Tasklet {
  private var tablePrefix = DEFAULT_TABLE_PREFIX
  private var historicRetentionMonth = DEFAULT_RETENTION_MONTH
  
  override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
    var totalCount = 0
    val date = LocalDate.now().minusDays(historicRetentionMonth.toLong())
    var rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT), date)
    LOG.info("Deleted rows number from the BATCH_STEP_EXECUTION_CONTEXT table: {}", rowCount)
    totalCount += rowCount
    rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_STEP_EXECUTION), date)
    LOG.info("Deleted rows number from the BATCH_STEP_EXECUTION table: {}", rowCount)
    totalCount += rowCount
    rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT), date)
    LOG.info("Deleted rows number from the BATCH_JOB_EXECUTION_CONTEXT table: {}", rowCount)
    totalCount += rowCount
    rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS), date)
    LOG.info("Deleted rows number from the BATCH_JOB_EXECUTION_PARAMS table: {}", rowCount)
    totalCount += rowCount
    rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_EXECUTION), date)
    LOG.info("Deleted rows number from the BATCH_JOB_EXECUTION table: {}", rowCount)
    totalCount += rowCount
    rowCount = jdbcTemplate.update(getQuery(SQL_DELETE_BATCH_JOB_INSTANCE))
    LOG.info("Deleted rows number from the BATCH_JOB_INSTANCE table: {}", rowCount)
    totalCount += rowCount
    contribution.incrementWriteCount(totalCount)
    return RepeatStatus.FINISHED
  }

  private fun getQuery(base: String?): String {
    return StringUtils.replace(base!!, "%PREFIX%", tablePrefix)
  }

  fun setTablePrefix(tablePrefix: String) {
    this.tablePrefix = tablePrefix
  }

  fun setHistoricRetentionMonth(historicRetentionMonth: Int) {
    this.historicRetentionMonth = historicRetentionMonth
  }

  companion object {
    /**
     * SQL statements removing step and job executions compared to a given date.
     */
    private const val SQL_DELETE_BATCH_STEP_EXECUTION_CONTEXT =
      "DELETE FROM %PREFIX%STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID IN (SELECT STEP_EXECUTION_ID FROM %PREFIX%STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  %PREFIX%JOB_EXECUTION where CREATE_TIME < ?))"
    private const val SQL_DELETE_BATCH_STEP_EXECUTION =
      "DELETE FROM %PREFIX%STEP_EXECUTION WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)"
    private const val SQL_DELETE_BATCH_JOB_EXECUTION_CONTEXT =
      "DELETE FROM %PREFIX%JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM  %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)"
    private const val SQL_DELETE_BATCH_JOB_EXECUTION_PARAMS =
      "DELETE FROM %PREFIX%JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID IN (SELECT JOB_EXECUTION_ID FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?)"
    private const val SQL_DELETE_BATCH_JOB_EXECUTION =
      "DELETE FROM %PREFIX%JOB_EXECUTION where CREATE_TIME < ?"
    private const val SQL_DELETE_BATCH_JOB_INSTANCE =
      "DELETE FROM %PREFIX%JOB_INSTANCE WHERE JOB_INSTANCE_ID NOT IN (SELECT JOB_INSTANCE_ID FROM %PREFIX%JOB_EXECUTION)"

    /**
     * Default value for the table prefix property.
     */
    private const val DEFAULT_TABLE_PREFIX = AbstractJdbcBatchMetadataDao.DEFAULT_TABLE_PREFIX

    /**
     * Default value for the data retention (in month)
     */
    private const val DEFAULT_RETENTION_MONTH = 180
  }
}