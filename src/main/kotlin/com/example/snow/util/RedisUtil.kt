package com.example.snow.util

import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Component

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, String>

) {
    fun setAndGetData(key: String, value: String, count: String): Long {
        //lua script 파일 사용 - key:value를 set하고 get
        //TODO - path- enum으로 따로 빼기
        val luaSetStockPath = "/lua/set-stock.lua"
        val setStockScript = DefaultRedisScript<Long>()
        setStockScript.setLocation(ClassPathResource(luaSetStockPath))
        setStockScript.setResultType(Long::class.java)
        val vStock = "$key:$value"

        return redisTemplate.execute(
            setStockScript, mutableListOf(vStock), count)
    }
}