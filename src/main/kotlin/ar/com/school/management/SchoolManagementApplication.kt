package ar.com.school.management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchoolManagementApplication

fun main(args: Array<String>) {
    runApplication<SchoolManagementApplication>(*args)
}
