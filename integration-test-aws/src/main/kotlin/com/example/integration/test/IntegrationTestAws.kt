package com.example.integration.test

import org.apache.http.util.Args
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationTestAws

fun main(args: Array<String>) {
    runApplication<IntegrationTestAws>(*args)
}