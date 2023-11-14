package com.ki960213.sheath.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Component

@Target(AnnotationTarget.CLASS)
@Component
annotation class UseCase

@Target(AnnotationTarget.CLASS)
@Component
annotation class Repository

@Target(AnnotationTarget.CLASS)
@Component
annotation class DataSource
