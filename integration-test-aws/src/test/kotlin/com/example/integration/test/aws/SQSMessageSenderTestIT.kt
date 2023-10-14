package com.example.integration.test.aws

import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import org.testcontainers.utility.DockerImageName
import java.time.Duration

@Testcontainers
@SpringBootTest
class SQSMessageSenderTestIT {

    @Autowired
    private lateinit var sender: SQSMessageSender

    @Autowired
    private lateinit var sqs: QueueMessagingTemplate

    companion object {
        @Container
        @JvmStatic
        val localStackContainer: LocalStackContainer = LocalStackContainer(DockerImageName.parse("localstack/localstack:0.13.0"))
            .withServices(LocalStackContainer.Service.SQS)

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            localStackContainer.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "test")
        }

        @DynamicPropertySource
        @JvmStatic
        fun overrideConfiguration(registry: DynamicPropertyRegistry) {
            registry.add("event-processing.order-event-queue") { "test" }
            registry.add("cloud.aws.sqs.endpoint") {
                localStackContainer.getEndpointOverride(
                    LocalStackContainer.Service.SQS
                )
            }
            registry.add("cloud.aws.credentials.access-key", localStackContainer::getAccessKey)
            registry.add("cloud.aws.credentials.secret-key", localStackContainer::getSecretKey)
        }
    }

    @Test
    fun `should send message to SQS`() {
        sender.sendMessage("Successful message")

        await().pollInterval(Duration.ofSeconds(2))
            .atMost(Duration.ofSeconds(10))
            .ignoreExceptions()
            .untilAsserted {
                val message = sqs.receive("test")

                Assertions.assertEquals(message!!.payload.toString(), "Successful message")
            }
    }
}