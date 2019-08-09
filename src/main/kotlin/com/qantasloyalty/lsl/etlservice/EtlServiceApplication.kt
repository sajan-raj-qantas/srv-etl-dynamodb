package com.qantasloyalty.lsl.etlservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EtlServiceApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<EtlServiceApplication>(*args)
		}
	}
}