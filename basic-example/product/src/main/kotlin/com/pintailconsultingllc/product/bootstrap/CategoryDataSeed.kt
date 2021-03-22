package com.pintailconsultingllc.product.bootstrap

import com.opencsv.CSVReaderHeaderAware
import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.repositories.CategoryRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.FileReader

private val logger = KotlinLogging.logger {}

@Component
class CategoryDataSeed(
    val categoryRepository: CategoryRepository
) : DataSeed {

    @Value("classpath:data/categories.csv")
    var categoriesCsv: Resource? = null

    override fun seed(dataSeedContext: DataSeedContext) {
        logger.info { "=====> START - Seeding categories" }
        try {
            if (categoriesCsv != null) {
                val csvReaderHeaderAware = CSVReaderHeaderAware(FileReader(categoriesCsv!!.file))
                var values: MutableMap<String, String>? = csvReaderHeaderAware.readMap()
                while (values != null) {
                    val found = values["name"]?.let { categoryRepository.findByName(it) }
                    if (found == null) {
                        val category = Category(name = values["name"]!!)
                        categoryRepository.save(category)
                        logger.info { "  Inserted category: ${category.name}" }
                    }
                    values = csvReaderHeaderAware.readMap()
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        logger.info { "=====> END - Seeding categories" }
    }
}