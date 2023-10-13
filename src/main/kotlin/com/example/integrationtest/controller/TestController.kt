package com.example.integrationtest.controller

import com.example.integrationtest.integration.CPFClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/sample")
class TestController(private val cpfClient: CPFClient) {

    @GetMapping
    fun test() {
        cpfClient.findCpf("CPF")
    }
}