package com.qantasloyalty.lsl.etlservice.model

import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDate

class ApplicationData {
    var applicationId: String? = null
    var riskAddressPostcode: String? = null
    var riskAddressState: String? = null
    var carParkingMethod: String? = null
    var carRedBookID: String? = null
    var carRegistrationNumber: String? = null
    var carUse: String? = null
    var carVehicleColour: String? = null
    var carVehicleCondition: String? = null
    var carVehicleFinanceType: String? = null
    var carAnnualMileage: String? = null
    var carAccessories: Boolean? = null
    var carFactoryOptions: Boolean? = null
    var carPreviousInsuranceCoverType: String? = null
    var carPreviousInsuranceExpiryDate: String? = null
    var carPreviousInsuranceHasPreviousInsurance: Boolean? = null
    var carNoInsuranceReason: String? = null
    var carPurchaseDetailsIsRecentPurchase: Boolean? = null
    var carMake: String? = null
    var carModel: String? = null
    var carRegistrationYear: String? = null
    var carBodyModifications: Boolean? = null
    var carOtherModifications: Boolean? = null
    var policyDetailsAdditionalExcessCode: String? = null
    var policyDetailsChoiceOfRepairer: Boolean? = null
    var policyDetailsCommencementDate: String? = null
    var policyDetailsCoverType: String? = null
    var policyDetailsHireCarOption: Boolean? = null
    var policyDetailsInstalmentplanNumber: String? = null
    var policyDetailsNoClaimsDiscount: String? = null
    var policyDetailsRoadsideOption: Boolean? = null
    var policyDetailsStampDutyExemption: Boolean? = null
    var policyDetailsWindscreenOption: Boolean? = null
    var policyDetailsNcdProtection: Boolean? = null
    var salesChannel: String? = null
    var InsuranceProductProductCode: String? = null
    var InsuranceProductProductGroup: String? = null
    var pricingMonthlyAmountPayable: String? = null
    var pricingMonthlyCanBuyOnline: Boolean? = null
    var pricingMonthlyFirstPayment: String? = null
    var pricingMonthlySubsequentPayment: String? = null
    var pricingMonthlyTotalPremium: String? = null
    var pricingMonthlyUnderwritingResults: String? = null
    var pricingYearlyAmountPayable: String? = null
    var pricingYearlyCanBuyOnline: Boolean? = null
    var pricingYearlyTotalPremium: String? = null
    var pricingYearlyUnderwritingResults: String? = null
    var underwritingKnockOut: Boolean? = null
    var applicationStatus: String? = null
    var applicationStartDate: LocalDate? = null
    var applicationLastModifiedDate: LocalDate? = null
    var lastPricingDate: LocalDate? = null
    var paymentType: String? = null

    fun fromApplicationMap(map: MutableMap<String, Any?>): ApplicationData {
        var mapJson = JSONObject(map)
        this.applicationId = mapJson.getString("applicationId")

        val attributesPolicyJson = JSONObject(mapJson.getString("attributes")).getJSONObject("policy")

        try {
            this.riskAddressPostcode = attributesPolicyJson.getJSONObject("riskAddress").getString("postcode")
            this.policyDetailsNcdProtection = if (attributesPolicyJson.getJSONObject("policyDetails").has("ncdProtection")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("ncdProtection") else null
            //TODO This field is in MotorApplication table. this.salesChannel: String? = if(null
            //TODO This field is hardcoded value in code. this.InsuranceProductProductCode: String? = if(null,
            //TODO This field is hardcoded value in code. this.InsuranceProductProductGroup: String? = null,
            if (mapJson.has("pricing")) {
                val pricingJson = JSONObject(mapJson.getString("pricing"))
                this.pricingMonthlyAmountPayable = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("amountPayable")
                this.pricingMonthlyCanBuyOnline = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getBoolean("canBuyOnline")
                this.pricingMonthlyFirstPayment = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("firstPayment")
                this.pricingMonthlySubsequentPayment = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("subsequentPayment")
                this.pricingMonthlyTotalPremium = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("totalPremium")
                this.pricingMonthlyUnderwritingResults = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("underwritingResult")
                this.pricingYearlyAmountPayable = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("amountPayable")
                this.pricingYearlyCanBuyOnline = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getBoolean("canBuyOnline")
                this.pricingYearlyTotalPremium = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("totalPremium")
                this.pricingYearlyUnderwritingResults = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("underwritingResult")
            }
            //TODO Populate this to true if either of convictions, suspensions , cancellations and maxdemerits is true. this.underwritingKnockOut: Boolean? = null,
            //TODO This field is in MotorApplication table. this.applicationStatus: String? = null,
            //TODO We need time when we started the motor application which is in MororApplication table. this.applicationStartDate: LocalDate,
            //TODO We need time when we last updated the motor application which is in MororApplication table.this.applicationLastModifiedDate: LocalDate,
            //TODO This field is in MotorApplication table this.lastPricingDate: LocalDate,
            //TODO Unable to locate this field in DB this.paymentType: String? = null
        } catch (e: JSONException) {
            println("Parsing error: ${e.message}")
        }
        return this
    }



    fun fromApplicationDataMap(map: MutableMap<String, Any?>): ApplicationData {
        var mapJson = JSONObject(map)
        this.applicationId = mapJson.getString("applicationId")

        val attributesPolicyJson = JSONObject(mapJson.getString("attributes")).getJSONObject("policy")

        try {
            this.riskAddressPostcode = attributesPolicyJson.getJSONObject("riskAddress").getString("postcode")
            this.riskAddressState = if (attributesPolicyJson.getJSONObject("riskAddress").has("state")) attributesPolicyJson.getJSONObject("riskAddress").getString("state") else null
            this.carParkingMethod = if (attributesPolicyJson.getJSONObject("car").has("carParkingMethod")) attributesPolicyJson.getJSONObject("car").getString("carParkingMethod") else null
            this.carRedBookID = if (attributesPolicyJson.getJSONObject("car").has("redbookId")) attributesPolicyJson.getJSONObject("car").getString("redbookId") else null
            this.carRegistrationNumber = if (attributesPolicyJson.getJSONObject("car").has("registrationYear")) attributesPolicyJson.getJSONObject("car").getString("registrationYear") else null
            this.carUse = if (attributesPolicyJson.getJSONObject("car").has("use")) attributesPolicyJson.getJSONObject("car").getString("use") else null
            this.carVehicleColour = if (attributesPolicyJson.getJSONObject("car").has("vehicleColour")) attributesPolicyJson.getJSONObject("car").getString("vehicleColour") else null
            this.carVehicleCondition = if (attributesPolicyJson.getJSONObject("car").has("vehicleCondition")) attributesPolicyJson.getJSONObject("car").getString("vehicleCondition") else null
            this.carVehicleFinanceType = if (attributesPolicyJson.getJSONObject("car").has("vehicleFinanceType")) attributesPolicyJson.getJSONObject("car").getString("vehicleFinanceType") else null
            this.carAnnualMileage = if (attributesPolicyJson.getJSONObject("car").has("annualMileage")) attributesPolicyJson.getJSONObject("car").getString("annualMileage") else null
            this.carAccessories = attributesPolicyJson.getJSONObject("car").has("accessories")
            this.carFactoryOptions = attributesPolicyJson.getJSONObject("car").has("factoryOptions")
            this.carPreviousInsuranceCoverType = if (attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").has("coverType")) attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").getString("coverType") else null
            this.carPreviousInsuranceExpiryDate = if (attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").has("expiryDate")) attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").getString("expiryDate") else null
            this.carPreviousInsuranceHasPreviousInsurance = if (attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").has("hasPreviousInsurance")) attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").getBoolean("hasPreviousInsurance") else null
            this.carNoInsuranceReason = if (attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").has("noInsuranceReasonDescription")) attributesPolicyJson.getJSONObject("car").getJSONObject("previousInsurance").getString("noInsuranceReasonDescription") else null
            this.carPurchaseDetailsIsRecentPurchase = if (attributesPolicyJson.getJSONObject("car").getJSONObject("purchaseDetails").has("isRecentPurchase")) attributesPolicyJson.getJSONObject("car").getJSONObject("purchaseDetails").getBoolean("isRecentPurchase") else null
            this.carMake = if (attributesPolicyJson.getJSONObject("car").has("make")) attributesPolicyJson.getJSONObject("car").getString("make") else null
            this.carModel = if (attributesPolicyJson.getJSONObject("car").has("model")) attributesPolicyJson.getJSONObject("car").getString("model") else null
            this.carRegistrationYear = if (attributesPolicyJson.getJSONObject("car").has("registrationYear")) attributesPolicyJson.getJSONObject("car").getString("registrationYear") else null
            this.carBodyModifications = attributesPolicyJson.getJSONObject("car").has("bodyModifications")
            this.carOtherModifications = attributesPolicyJson.getJSONObject("car").has("otherModifications")
            this.policyDetailsAdditionalExcessCode = if (attributesPolicyJson.getJSONObject("policyDetails").has("additionalExcessCode")) attributesPolicyJson.getJSONObject("policyDetails").getString("additionalExcessCode") else null
            this.policyDetailsChoiceOfRepairer = if (attributesPolicyJson.getJSONObject("policyDetails").has("choiceOfRepairer")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("choiceOfRepairer") else null
            this.policyDetailsCommencementDate = if (attributesPolicyJson.getJSONObject("policyDetails").has("commencementDate")) attributesPolicyJson.getJSONObject("policyDetails").getString("commencementDate") else null
            this.policyDetailsCoverType = if (attributesPolicyJson.getJSONObject("policyDetails").has("coverType")) attributesPolicyJson.getJSONObject("policyDetails").getString("coverType") else null
            this.policyDetailsHireCarOption = if (attributesPolicyJson.getJSONObject("policyDetails").has("hireCarOption")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("hireCarOption") else null
            this.policyDetailsInstalmentplanNumber = if (attributesPolicyJson.getJSONObject("policyDetails").has("instalmentPlanNumber")) attributesPolicyJson.getJSONObject("policyDetails").getString("instalmentPlanNumber") else null
            this.policyDetailsNoClaimsDiscount = if (attributesPolicyJson.getJSONObject("policyDetails").getJSONObject("noClaimsDiscount").has("type")) attributesPolicyJson.getJSONObject("policyDetails").getJSONObject("noClaimsDiscount").getString("type") else null
            this.policyDetailsRoadsideOption = if (attributesPolicyJson.getJSONObject("policyDetails").has("roadsideOption")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("roadsideOption") else null
            this.policyDetailsStampDutyExemption = if (attributesPolicyJson.getJSONObject("policyDetails").has("stampDutyExemption")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("stampDutyExemption") else null
            this.policyDetailsWindscreenOption = if (attributesPolicyJson.getJSONObject("policyDetails").has("windscreenOption")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("windscreenOption") else null
            this.policyDetailsNcdProtection = if (attributesPolicyJson.getJSONObject("policyDetails").has("ncdProtection")) attributesPolicyJson.getJSONObject("policyDetails").getBoolean("ncdProtection") else null
            //TODO This field is in MotorApplication table. this.salesChannel: String? = if(null
            //TODO This field is hardcoded value in code. this.InsuranceProductProductCode: String? = if(null,
            //TODO This field is hardcoded value in code. this.InsuranceProductProductGroup: String? = null,
            if (mapJson.has("pricing")) {
                val pricingJson = JSONObject(mapJson.getString("pricing"))
                this.pricingMonthlyAmountPayable = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("amountPayable")
                this.pricingMonthlyCanBuyOnline = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getBoolean("canBuyOnline")
                this.pricingMonthlyFirstPayment = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("firstPayment")
                this.pricingMonthlySubsequentPayment = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("subsequentPayment")
                this.pricingMonthlyTotalPremium = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("totalPremium")
                this.pricingMonthlyUnderwritingResults = getPricing(pricingJson, InstalmentPlanNumber.MONTHLY_PAYMENT)?.getString("underwritingResult")
                this.pricingYearlyAmountPayable = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("amountPayable")
                this.pricingYearlyCanBuyOnline = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getBoolean("canBuyOnline")
                this.pricingYearlyTotalPremium = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("totalPremium")
                this.pricingYearlyUnderwritingResults = getPricing(pricingJson, InstalmentPlanNumber.ANNUAL_PAYMENT)?.getString("underwritingResult")
            }
            //TODO Populate this to true if either of convictions, suspensions , cancellations and maxdemerits is true. this.underwritingKnockOut: Boolean? = null,
            //TODO This field is in MotorApplication table. this.applicationStatus: String? = null,
            //TODO We need time when we started the motor application which is in MororApplication table. this.applicationStartDate: LocalDate,
            //TODO We need time when we last updated the motor application which is in MororApplication table.this.applicationLastModifiedDate: LocalDate,
            //TODO This field is in MotorApplication table this.lastPricingDate: LocalDate,
            //TODO Unable to locate this field in DB this.paymentType: String? = null
        } catch (e: JSONException) {
            println("Parsing error: ${e.message}")
        }
        return this
    }

    private fun getPricing(pricingJson: JSONObject, instalmentPlanNumber: InstalmentPlanNumber): JSONObject? {
        var price: JSONObject? = null
        val pricesArray = pricingJson.getJSONObject("details").getJSONArray("prices")
        for (i in 0 until pricesArray.length()) {
            val price = pricesArray.getJSONObject(i)
            if (instalmentPlanNumber.toString().equals(price.getString("instalmentPlanNumber"))) {
                return price
            }
        }
        return price
    }

    override fun toString(): String {
        return "ApplicationData(applicationId=$applicationId, riskAddressPostcode=$riskAddressPostcode, riskAddressState=$riskAddressState, carParkingMethod=$carParkingMethod, carRedBookID=$carRedBookID, carRegistrationNumber=$carRegistrationNumber, carUse=$carUse, carVehicleColour=$carVehicleColour, carVehicleCondition=$carVehicleCondition, carVehicleFinanceType=$carVehicleFinanceType, carAnnualMileage=$carAnnualMileage, carAccessories=$carAccessories, carFactoryOptions=$carFactoryOptions, carPreviousInsuranceCoverType=$carPreviousInsuranceCoverType, carPreviousInsuranceExpiryDate=$carPreviousInsuranceExpiryDate, carPreviousInsuranceHasPreviousInsurance=$carPreviousInsuranceHasPreviousInsurance, carNoInsuranceReason=$carNoInsuranceReason, carPurchaseDetailsIsRecentPurchase=$carPurchaseDetailsIsRecentPurchase, carMake=$carMake, carModel=$carModel, carRegistrationYear=$carRegistrationYear, carBodyModifications=$carBodyModifications, carOtherModifications=$carOtherModifications, policyDetailsAdditionalExcessCode=$policyDetailsAdditionalExcessCode, policyDetailsChoiceOfRepairer=$policyDetailsChoiceOfRepairer, policyDetailsCommencementDate=$policyDetailsCommencementDate, policyDetailsCoverType=$policyDetailsCoverType, policyDetailsHireCarOption=$policyDetailsHireCarOption, policyDetailsInstalmentplanNumber=$policyDetailsInstalmentplanNumber, policyDetailsNoClaimsDiscount=$policyDetailsNoClaimsDiscount, policyDetailsRoadsideOption=$policyDetailsRoadsideOption, policyDetailsStampDutyExemption=$policyDetailsStampDutyExemption, policyDetailsWindscreenOption=$policyDetailsWindscreenOption, policyDetailsNcdProtection=$policyDetailsNcdProtection, salesChannel=$salesChannel, InsuranceProductProductCode=$InsuranceProductProductCode, InsuranceProductProductGroup=$InsuranceProductProductGroup, pricingMonthlyAmountPayable=$pricingMonthlyAmountPayable, pricingMonthlyCanBuyOnline=$pricingMonthlyCanBuyOnline, pricingMonthlyFirstPayment=$pricingMonthlyFirstPayment, pricingMonthlySubsequentPayment=$pricingMonthlySubsequentPayment, pricingMonthlyTotalPremium=$pricingMonthlyTotalPremium, pricingMonthlyUnderwritingResults=$pricingMonthlyUnderwritingResults, pricingYearlyAmountPayable=$pricingYearlyAmountPayable, pricingYearlyCanBuyOnline=$pricingYearlyCanBuyOnline, pricingYearlyTotalPremium=$pricingYearlyTotalPremium, pricingYearlyUnderwritingResults=$pricingYearlyUnderwritingResults, underwritingKnockOut=$underwritingKnockOut, applicationStatus=$applicationStatus, applicationStartDate=$applicationStartDate, applicationLastModifiedDate=$applicationLastModifiedDate, lastPricingDate=$lastPricingDate, paymentType=$paymentType)"
    }

    private enum class InstalmentPlanNumber {
        MONTHLY_PAYMENT, ANNUAL_PAYMENT
    }


}
