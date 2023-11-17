package com.ki960213.sheath.annotation

import org.atteo.classindex.IndexAnnotated

@IndexAnnotated
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Component

@IndexAnnotated
@Target(AnnotationTarget.CLASS)
@Component
annotation class UseCase

@IndexAnnotated
@Target(AnnotationTarget.CLASS)
@Component
annotation class Repository

@IndexAnnotated
@Target(AnnotationTarget.CLASS)
@Component
annotation class DataSource
