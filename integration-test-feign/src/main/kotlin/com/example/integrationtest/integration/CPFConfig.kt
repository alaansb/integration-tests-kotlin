package com.example.integrationtest.integration

import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class CPFConfig {

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return CPFClientDecoder()
    }
}