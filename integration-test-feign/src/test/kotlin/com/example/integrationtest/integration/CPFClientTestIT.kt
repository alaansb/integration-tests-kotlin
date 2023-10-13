package com.example.integrationtest.integration

import com.example.integrationtest.IntegrationTestApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.wiremock.integrations.testcontainers.WireMockContainer

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = [IntegrationTestApplication::class])
@ImportAutoConfiguration(classes = [FeignAutoConfiguration::class])
class CPFClientTestIT {

    @LocalServerPort
    private val port: Int = 0

    companion object {
        @Container
        @JvmStatic
        private val wiremockServer: WireMockContainer = WireMockContainer("wiremock/wiremock:2.35.0")
                .withMapping("photos-by-album",
                        CPFClientTestIT::class.java,
                        "mocks-config.json")

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("cpf-client.url", wiremockServer::getBaseUrl)
        }
    }

    @Autowired
    private lateinit var cpfClient: CPFClient
    
    @Test
    fun `It should get CPF`() {
        assertDoesNotThrow { cpfClient.findCpf("1234") }
    }

    @Test
    fun `It throw should get CPF`() {
        assertThrows<Exception> { cpfClient.findCpf("567") }
    }
}