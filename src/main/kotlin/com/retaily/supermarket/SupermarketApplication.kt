package com.retaily.supermarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.retaily.supermarket", "com.retaily.common"])
class SupermarketApplication

fun main(args: Array<String>) {
	runApplication<SupermarketApplication>(*args)
}
