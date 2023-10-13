package com.example.integrationtest.exception

class NotFoundException(
        override val message: String
): RuntimeException()