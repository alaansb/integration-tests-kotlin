package com.example.integrationtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class IntegrationTestApplication

fun main(args: Array<String>) {
	runApplication<IntegrationTestApplication>(*args)
}
