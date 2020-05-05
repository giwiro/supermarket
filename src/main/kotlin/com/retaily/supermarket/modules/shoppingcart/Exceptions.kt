package com.retaily.supermarket.modules.shoppingcart

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/*
@ResponseStatus(HttpStatus.BAD_REQUEST)
class NonExistentProductException(message:String): RuntimeException(message)*/

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NonExistentShoppingCartItemException(message:String): RuntimeException(message)