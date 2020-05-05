package com.retaily.supermarket.common.web

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class NotInSessionException(message:String): RuntimeException(message)