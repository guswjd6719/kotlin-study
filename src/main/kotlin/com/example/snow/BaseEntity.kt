package com.example.snow

import jakarta.persistence.*
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(columnDefinition = "boolean default true")
    var active: Boolean = true

    override fun toString(): String = ToStringBuilder.reflectionToString(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        val otherEntity = (other as? BaseEntity) ?: return false
        return this.id == otherEntity.id
    }

    override fun hashCode(): Int {
        val prime = 59
        val result = 1
        return result * prime + (id?.hashCode() ?: 43)
    }
}