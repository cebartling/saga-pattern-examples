package com.pintailconsultingllc.product.controllers

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.Pageable

abstract class PageMixin<T> @JsonCreator constructor(
    @JsonProperty("content") content: List<T>?,
    @JsonProperty("pageable") pageable: Pageable?,
    @JsonProperty("total") total: Long
)
