package com.qantasloyalty.lsl.etlservice.model

data class Car (
     var ageRestriction: String? = null,
     var annualMileage: String? = null,
     var modified: Boolean? = null,
     var parkingMethod: String? = null,
     var redbookId: String? = null,
     var registrationNumber: String? = null,
     var registrationYear: Int? = null,
     var use: String? = null,
     var vehicleColour: String? = null,
     var vehicleCondition: String? = null,
     var vehicleFinanceType: String? = null,
     var accessories: MutableList<CarAccessory>? = null, //Boolean
     var factoryOptions: MutableList<FactoryOption>? = null, //Boolean
     var insuredValueDetails: CarInsuredValueDetails? = null,
     var previousInsurance: CarPreviousInsurance? = null,
     var purchaseDetails: CarPurchaseDetails? = null,
     var make: String? = null,
     var makeDescription: String? = null,
     var model: String? = null,
     var modelDescription: String? = null,
     var bodyModifications: MutableList<String>? = null,
     var otherModifications: MutableList<String>? = null,
     var body: String? = null,
     var fuel: String? = null,
     var transmission: String? = null,
     var marketValue: Int? = null){
}

data class CarInsuredValueDetails (
     var agreedValue: Int? = null,
     var marketOrAgreedValue: String? = null){
}

data class CarPreviousInsurance (
     var companyCode: String? = null,
     var expiryDate: String? = null,
     var coverType: String? = null,
     var noInsuranceReason: String? = null,
     var noInsuranceReasonDescription: String? = null,
     var companyName: String? = null,
     var hasPreviousInsurance: Boolean? = null){
}

data class CarPurchaseDetails (
     var purchaseDate: String? = null,
     var isRecentPurchase: Boolean? = null,
     var purchasePrice: Int? = null){
}

data class CarAccessory (
     var includedInPurchasePrice: Boolean? = null,
     var insuredAmount: Int? = null,
     var itemType: String? = null){
}

data class FactoryOption (
     var includedInPurchasePrice: Boolean? = null,
     var insuredAmount: Int? = null,
     var itemType: String? = null){
}
