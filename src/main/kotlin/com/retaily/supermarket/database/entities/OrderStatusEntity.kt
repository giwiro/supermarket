package com.retaily.supermarket.database.entities

import java.util.Optional
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "order_status")
class OrderStatusEntity {
    @Id
    var orderStatusId: Long? = null
    var orderStatusName: String? = null

    @Override
    override fun toString(): String {
        return this.orderStatusName!!
    }
}

@Repository
interface OrderStatusRepository : CrudRepository<OrderStatusEntity, Long> {
    override fun findById(id: Long): Optional<OrderStatusEntity>
}
