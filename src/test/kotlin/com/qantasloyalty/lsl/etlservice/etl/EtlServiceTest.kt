package com.qantasloyalty.lsl.etlservice.etl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EtlServiceTest {

	@Autowired
	private lateinit var etlService: EtlService

	@Test
	fun testEtlService() {
		etlService.doEtl()
		Thread.sleep(10000)
	}

}
