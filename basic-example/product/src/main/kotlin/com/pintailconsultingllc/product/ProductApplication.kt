package com.pintailconsultingllc.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
