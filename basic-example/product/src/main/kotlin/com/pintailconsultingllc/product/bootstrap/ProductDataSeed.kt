package com.pintailconsultingllc.product.bootstrap

import com.opencsv.CSVReaderHeaderAware
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.jpa.repositories.ProductRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.FileReader

private val logger = KotlinLogging.logger {}

@Component
class ProductDataSeed(
    val productRepository: ProductRepository
) : DataSeed {

    @Value("classpath:data/products.csv")
    var csv: Resource? = null

    override fun seed(dataSeedContext: DataSeedContext) {
        logger.info { "=====> START - Seeding products" }
        try {
            if (csv != null) {
                val csvReaderHeaderAware = CSVReaderHeaderAware(FileReader(csv!!.file))
                var values: MutableMap<String, String>? = csvReaderHeaderAware.readMap()
                while (values != null) {
                    val found = values["name"]?.let { productRepository.findByName(it) }
                    if (found == null) {
                        val product = Product(name = values["name"]!!, sku = values["sku"]!!)
                        productRepository.save(product)
                        logger.info { "  Inserted product: ${product.name}" }
                    }
                    values = csvReaderHeaderAware.readMap()
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        logger.info { "=====> END - Seeding products" }
    }
}