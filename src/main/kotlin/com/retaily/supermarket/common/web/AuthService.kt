package com.retaily.supermarket.common.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class SessionService(@Autowired val session: HttpSession) {
    fun getUserId(): Long? {
        val userIdStr = session.getAttribute("userId")
        if (userIdStr != null) {
            return userIdStr.toString().toLong()
        }
        return null
    }
}