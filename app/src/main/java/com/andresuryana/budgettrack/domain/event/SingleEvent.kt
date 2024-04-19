package com.andresuryana.budgettrack.domain.event

open class SingleEvent<out T>(private val content: T) {

    var isHandled = false
        private set // Allow external read access, but not write access

    /**
     * Returns the content and prevents it's use again.
     */
    fun getIfNotHandled(): T? {
        return if (isHandled) null
        else {
            isHandled = true
            content
        }
    }
}