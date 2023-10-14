package com.example.integration.test.aws

import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SQSMessageSender(
    private val sender: QueueMessagingTemplate,
    @Value("\${event-processing.order-event-queue}")
    private val queue: String
) {
    fun sendMessage(message: String) {
        sender.convertAndSend(queue, message)
    }
}
