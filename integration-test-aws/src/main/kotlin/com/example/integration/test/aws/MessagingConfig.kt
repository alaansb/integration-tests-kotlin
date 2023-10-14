package com.example.integration.test.aws

import com.amazonaws.services.sqs.AmazonSQSAsync
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessagingConfig {
    @Bean
    fun queueMessagingTemplate(amazonSQS: AmazonSQSAsync): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQS)
    }
}
