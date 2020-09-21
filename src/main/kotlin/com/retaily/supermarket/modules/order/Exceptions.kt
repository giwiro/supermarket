package com.retaily.supermarket.modules.order

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFound(message: String) : RuntimeException(message)
