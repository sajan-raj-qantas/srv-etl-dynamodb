package com.qantasloyalty.lsl.etlservice.etl

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChannelExploreTest {

	private lateinit var channelExplore: ChannelExplore

	@BeforeAll
	fun init(){
		channelExplore = ChannelExplore()
	}

	@Test
	fun testExplore() {
		channelExplore.exploreChannel()
		//Thread.sleep(10000)
	}

}
