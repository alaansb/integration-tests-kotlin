package com.example.integrationtest.integration

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "cpf-client", url = "\${cpf-client.url}", configuration = [CPFConfig::class])
interface CPFClient {

    @GetMapping("/{cpf}")
    fun findCpf(@PathVariable("cpf") cpf: String)
}