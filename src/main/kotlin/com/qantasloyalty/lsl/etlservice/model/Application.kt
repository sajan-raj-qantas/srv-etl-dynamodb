package com.qantasloyalty.lsl.etlservice.model


data class Application(
         val applicationId:String,
         val createdTimeStamp: String?,
         val lastDataCreatedTimestamp: String?,
         val lastUpdatedTimeStamp: String?,
         val status: String?,
         val salesChannel: String?,
         val lastPricingRequestedTimestamp:String?) {
}

data class ApplicationDataKey(
        val applicationId:String,
        val lastDataCreatedTimestamp: String?){}
