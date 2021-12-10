package com.soonyong.hong.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class PersonalBatchApplication

fun main(args: Array<String>) {
    runApplication<PersonalBatchApplication>(*args)
}
