package com.soonyong.hong.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
@EnableBatchProcessing
class PersonalBatchApplication

fun main(args: Array<String>) {
    val context = runApplication<PersonalBatchApplication>(*args)
    val exitCode = SpringApplication.exit(context)
    exitProcess(exitCode)
}
