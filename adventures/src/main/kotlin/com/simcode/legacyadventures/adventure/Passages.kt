package com.simcode.legacyadventures.adventure

data class Passages(val description: String, private val passages: List<Passage>) {

    fun findByLabel(label: PassageLabel) = passages.find { it.label == label }

    fun getAll() = passages

}