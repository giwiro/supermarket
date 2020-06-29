package com.retaily.supermarket.modules.root.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket"])
class RootController(@Autowired val buildProperties: BuildProperties) {
    @GetMapping(path = ["/version"])
    fun getVersion() = mapOf("version" to buildProperties.version)
}
