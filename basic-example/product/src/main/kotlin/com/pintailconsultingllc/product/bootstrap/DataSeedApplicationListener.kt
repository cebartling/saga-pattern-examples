package com.pintailconsultingllc.product.bootstrap

import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class DataSeedApplicationListener(
    val categoryDataSeed: CategoryDataSeed
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        logger.info { "===> START: Data seeding" }
        val context = DataSeedContext()
        categoryDataSeed.seed(context)
        logger.info { "===> END: Data seeding" }
    }
}