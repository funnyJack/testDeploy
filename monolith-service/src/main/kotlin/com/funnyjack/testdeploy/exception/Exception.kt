package com.funnyjack.testdeploy.exception

import com.hiczp.spring.error.NotFoundException
import kotlin.reflect.KClass

private const val UNKNOWN_RESOURCE = "Unknown Resource"

class ResourceNotFoundException(message: String = "Request resource not found", cause: Throwable? = null) :
    NotFoundException(message, cause) {
    constructor(resourceName: String) : this("Request $resourceName not found", null)
    constructor(kClass: KClass<*>) : this(kClass.simpleName ?: UNKNOWN_RESOURCE)
}