package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.OrderUser
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "order_user")
class OrderUserEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderUserId: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null

    constructor(firstName: String, lastName: String, email: String) : this() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    fun mapToModel(): OrderUser = OrderUser(orderUserId!!, firstName!!, lastName!!, email!!)
}

@Repository
interface OrderUserRepository : CrudRepository<OrderUserEntity, Long> {
    fun save(entity: OrderUserEntity): OrderUserEntity
}
