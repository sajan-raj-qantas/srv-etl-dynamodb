package com.qantasloyalty.lsl.etlservice.model

data class Policy (
     var policyType: String? = null,
     var car: Car? = null,
     var insuranceProduct: InsuranceProduct? = null,
     var policyDetails: PolicyDetails? = null,
     var postalAddress: Address? = null,
     var riskAddress: Address? = null){
}

data class InsuranceProduct (
     var productCode: String? = null,
     var productGroup: String? = null){
}

data class PolicyDetails (
     var additionalExcessCode: String? = null,
     var choiceOfRepairer: Boolean? = null,
     var commencementDate: String? = null,
     var coverType: String? = null,
     var hireCarOption: Boolean? = null,
     var instalmentPlanNumber: String? = null,
     var noClaimsDiscount: PolicyDetailsNoClaimsDiscount? = null,
     var paymentCycleDate: String? = null,
     var roadsideOption: Boolean? = null,
     var stampDutyExemption: String? = null,
     var windscreenOption: Boolean? = null,
     var ncdProtection: Boolean? = null){
}

data class PolicyDetailsNoClaimsDiscount (
     var type: String? = null){
    override fun toString(): String {
        return if(type!=null) type!! else ""
    }
}

data class Address (
     var dpid: String? = null,
     var number: String? = null,
     var postalDeliveryNumber: String? = null,
     var postcode: String? = null,
     var state: String? = null,
     var street: String? = null,
     var suburb: String? = null,
     var type: String? = null,
     var unit: String? = null){
}
