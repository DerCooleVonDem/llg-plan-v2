package com.johannes.llgplanv2.api

fun main() {
    val eventList = EventList()
    eventList.sync()
    val list = eventList.list ?: mutableListOf()
    for (event in list) {
        println("${event.date} : ${event.gradeLevel} : ${event.text}")
    }
}