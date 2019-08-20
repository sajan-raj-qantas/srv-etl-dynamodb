package com.qantasloyalty.lsl.etlservice.etl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EtlTest {

	@Autowired
	private lateinit var etl: Etl

	@Test
	fun testEtl() {
		etl.doEtl(3)
	}

}
