package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class HelloResource {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/hello/flux")
    fun helloFlux(): Flux<String> {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map { authentication -> authentication.principal }
            .flatMapMany { principal ->
                Flux.fromIterable(listOf("hello", "world", principal.toString()))
            }
    }

    @GetMapping("/hello/mono")
    fun helloMono(): Mono<List<String>> {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map { authentication -> authentication.principal }
            .flatMapMany { principal ->
                Flux.fromIterable(listOf("hello", "world", principal.toString()))
            }
            .collectList()
    }
}