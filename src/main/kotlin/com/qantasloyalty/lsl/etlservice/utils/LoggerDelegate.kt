package com.qantasloyalty.lsl.etlservice.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerDelegate : ReadOnlyProperty<Any, Logger> {
    private lateinit var logger: Logger

    override fun getValue(thisRef: Any, property: KProperty<*>): Logger {
        if (!::logger.isInitialized) {
            logger = if (thisRef::class.isCompanion) {
                LoggerFactory.getLogger(thisRef.javaClass.canonicalName.dropLast(10))
            } else {
                LoggerFactory.getLogger(thisRef.javaClass)
            }
        }
        return logger
    }
}

val log: ReadOnlyProperty<Any, Logger> get() = LoggerDelegate()