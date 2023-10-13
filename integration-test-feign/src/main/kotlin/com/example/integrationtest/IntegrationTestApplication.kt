package com.example.integrationtest

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@ComponentScan("com.example.integrationtest")
class IntegrationTestApplication