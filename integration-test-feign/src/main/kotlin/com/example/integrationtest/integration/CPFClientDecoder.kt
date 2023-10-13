package com.example.integrationtest.integration

import com.example.integrationtest.exception.NotFoundException
import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception
import java.lang.RuntimeException

class CPFClientDecoder: ErrorDecoder {
    override fun decode(key: String, response: Response): Exception {

        if (response.status() == 404) {
            throw NotFoundException("CPF not found")
        }

        throw RuntimeException("Error when call CPF API")
    }
}