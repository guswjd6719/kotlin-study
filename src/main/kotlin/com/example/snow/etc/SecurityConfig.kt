package com.example.snow.etc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager = false)
class SecurityConfig {
    @Bean
    fun webHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            cors { disable() }
            csrf { disable() }
            securityMatcher(PathPatternParserServerWebExchangeMatcher("/member/sign-in"))
            authorizeExchange {
                authorize(anyExchange, authenticated)
            }
            formLogin { disable() }
            httpBasic {

            }
        }
    }

}