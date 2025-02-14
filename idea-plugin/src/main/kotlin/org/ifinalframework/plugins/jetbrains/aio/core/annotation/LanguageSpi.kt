package org.ifinalframework.plugins.jetbrains.aio.core.annotation

import kotlin.reflect.KClass


/**
 * LanguageSpi
 *
 * @author iimik
 * @since 0.0.1
 **/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class LanguageSpi(
    vararg val value: KClass<*> = [],
)
