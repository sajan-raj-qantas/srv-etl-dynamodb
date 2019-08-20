package com.qantasloyalty.lsl.etlservice.etl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BatchGetTest {

	@Autowired
	private lateinit var etlService: EtlService

	@Test
	fun testEtlService() {
		etlService.doEtl(1)
		Thread.sleep(10000)
	}



}
