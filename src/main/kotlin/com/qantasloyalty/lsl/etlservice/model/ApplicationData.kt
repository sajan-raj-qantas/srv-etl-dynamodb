package com.qantasloyalty.lsl.etlservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

data class ApplicationData(

        var applicationId: String? = null,
        @JsonIgnore
        var attributes: ApplicationAttributes? = null,
        @JsonIgnore
        var pricing: Pricing? = null,
       // var salesChannel: String? = null, // from Application
       // var startChannel: String? = null, //TODO find out source
       // var applicationStatus: String? = null, // from Application
        var createdTimestamp: String? = null, // from Application
        var lastUpdatedTimestamp: String? = null, // from Application
        //var lastPricingRequestedTimestamp: String? = null, // from Application
        var paymentType: String? = null,
        var pricingEmailedTimestamp: String? = null

) {
    companion object {
        fun above90AppHeading(): String {
            var headingBuilder: StringBuilder = java.lang.StringBuilder()
            headingBuilder.append("Application ID,")
            headingBuilder.append("campaign.offerId,")
            headingBuilder.append("Car.registrationNumber,")
            headingBuilder.append("Car.use,")
            headingBuilder.append("Car.accessories,")
            headingBuilder.append("Car.factoryOptions,")
            headingBuilder.append("Car.previouInsurance.companyName,")
            headingBuilder.append("Car.previousInsurance.expiryDate,")
            headingBuilder.append("Car.purchaseDetails.purchasePrice,")
            headingBuilder.append("Car.purchaseDetails.purchaseDate,")
            headingBuilder.append("Car.make,")
            headingBuilder.append("Car.model,")
            headingBuilder.append("Car.body,")
            headingBuilder.append("Car.registrationYear,")
            headingBuilder.append("Car.bodyModifications,")
            headingBuilder.append("Car.otherModiciations,")
            headingBuilder.append("Car.vehicleFinanceType,")
            headingBuilder.append("PolicyDetails.additionalExcessCode,")
            headingBuilder.append("PolicyDetails.choiceOfRepairer,")
            headingBuilder.append("PolicyDetails.commencementDate,")
            headingBuilder.append("PolicyDetails.coverType,")
            headingBuilder.append("PolicyDetails.hireCarOption,")
            headingBuilder.append("PolicyDetails.instalmentPlanNumber,")
            headingBuilder.append("PolicyDetails.noClaimsDiscount,")
            headingBuilder.append("PolicyDetails.roadsideOption,")
            headingBuilder.append("PolicyDetails.stampDutyExemption,")
            headingBuilder.append("PolicyDetails.windscreenOption,")
            headingBuilder.append("PolicyDetails.ncdProtection,")
            //headingBuilder.append("salesChannel,")
            //headingBuilder.append("startChannel,")
            headingBuilder.append("InsuranceProduct.productCode,")
            headingBuilder.append("InsuranceProduct.productGroup,")
            headingBuilder.append("Pricing[Monthly].canBuyOnline,")
            headingBuilder.append("Pricing[Monthly].totalPremium,")
            headingBuilder.append("Pricing[Monthly].underwritingResults,")
            headingBuilder.append("Pricing[Yearly].canBuyOnline,")
            headingBuilder.append("Pricing[Yearly].totalPremium,")
            headingBuilder.append("Pricing[Yearly].underwritingResults,")
            headingBuilder.append("knockout,")
            //headingBuilder.append("applicationStatus,")
            headingBuilder.append("ApplicationStartDate,")
            headingBuilder.append("ApplicationLastModifiedDate,")
            //headingBuilder.append("lastPricingDate,")
            headingBuilder.append("TotalNoOfHouseHoldDrivers,")
            headingBuilder.append("TotalDrivers,")
            headingBuilder.append("PaymentType,")
            headingBuilder.append("HasReachedQuoteStage,")
            headingBuilder.append("HasEmailedQuote,")
            headingBuilder.append("HasProgressedPastQuoteStage,")
            headingBuilder.append("HasReachedSaleStage")
            return headingBuilder.toString()
        }

        fun below90PartyHeading(): String {
            var headingBuilder: StringBuilder = java.lang.StringBuilder()
            headingBuilder.append("Application ID,")
            headingBuilder.append("Party.title,")
            headingBuilder.append("Party.dateOfBirth,")
            headingBuilder.append("Party.lastName,")
            headingBuilder.append("Party.firstName,")
            headingBuilder.append("Party.ownsAnotherCar,")
            headingBuilder.append("Party.ownsHome,")
            headingBuilder.append("Party.relationshipToPolicyHolder,")
            headingBuilder.append("Party.relationshipToRegularDriver,")
            headingBuilder.append("Party.qffNumber,")
            headingBuilder.append("Party.iLoyalMemberId,")
            headingBuilder.append("Party.contactDetails.emailAddress,")
            headingBuilder.append("Party.contactDetails.mobileNumber,")
            headingBuilder.append("Party.drivingHistory.claims,")
            headingBuilder.append("Party.licence.licenceType,")
            headingBuilder.append("Party.licence.date,")
            headingBuilder.append("Party.licence.country,")
            headingBuilder.append("Party.livesAtPolicyHolderAddress,")
            headingBuilder.append("Party.gender,")
            headingBuilder.append("Party.retired,")
            headingBuilder.append("Party.authorisedToPolicy,")
            headingBuilder.append("Party.roles")
            return headingBuilder.toString()
        }

        fun below90AppHeading(): String {
            var headingBuilder: StringBuilder = java.lang.StringBuilder()
            headingBuilder.append("Application ID,")
            headingBuilder.append("Campaign.offerId,")
            headingBuilder.append("RiskAddress.Postcode,")
            headingBuilder.append("RiskAddress.State,")
            headingBuilder.append("Car.parkingMethod,")
            headingBuilder.append("Car.redBookID,")
            headingBuilder.append("Car.registrationNumber,")
            headingBuilder.append("Car.use,")
            headingBuilder.append("Car.vehicleColour,")
            headingBuilder.append("Car.vehicleCondition,")
            headingBuilder.append("Car.vehicleFinanceType,")
            headingBuilder.append("Car.annualMileage,")
            headingBuilder.append("Car.accessories,")
            headingBuilder.append("Car.factoryOptions,")
            headingBuilder.append("Car.previousInsurance.coverType,")
            headingBuilder.append("Car.previousInsurance.expiryDate,")
            headingBuilder.append("Car.previousInsurance.hasPreviousInsurance,")
            headingBuilder.append("Car.previousInsurance.noInsuranceReason,")
            headingBuilder.append("Car.previousInsurance.noInsuranceReasonDescription,")
            headingBuilder.append("Car.previouInsurance.companyName,")
            headingBuilder.append("Car.purchaseDetails.isRecentPurchase,")
            headingBuilder.append("Car.purchaseDetails.purchaseDate,")
            headingBuilder.append("Car.purchaseDetails.purchasePrice,")
            headingBuilder.append("Car.ageRestriction,")
            headingBuilder.append("Car.make,")
            headingBuilder.append("Car.fuel,")
            headingBuilder.append("Car.transmission,")
            headingBuilder.append("Car.body,")
            headingBuilder.append("Car.model,")
            headingBuilder.append("Car.registrationYear,")
            headingBuilder.append("Car.marketValue,")
            headingBuilder.append("Car.bodyModifications,")
            headingBuilder.append("Car.otherModiciations,")
            headingBuilder.append("PolicyDetails.additionalExcessCode,")
            headingBuilder.append("PolicyDetails.choiceOfRepairer,")
            headingBuilder.append("PolicyDetails.commencementDate,")
            headingBuilder.append("PolicyDetails.coverType,")
            headingBuilder.append("PolicyDetails.hireCarOption,")
            headingBuilder.append("PolicyDetails.instalmentPlanNumber,")
            headingBuilder.append("PolicyDetails.noClaimsDiscount,")
            headingBuilder.append("PolicyDetails.roadsideOption,")
            headingBuilder.append("PolicyDetails.stampDutyExemption,")
            headingBuilder.append("PolicyDetails.windscreenOption,")
            headingBuilder.append("PolicyDetails.ncdProtection,")
            headingBuilder.append("PolicyDetails.stampDutyExemption,")
            //headingBuilder.append("salesChannel,")
            headingBuilder.append("InsuranceProduct.productCode,")
            headingBuilder.append("InsuranceProduct.productGroup,")
            headingBuilder.append("Pricing[Monthly].amountPayable,")
            headingBuilder.append("Pricing[Monthly].canBuyOnline,")
            headingBuilder.append("Pricing[Monthly].firstPayment,")
            headingBuilder.append("Pricing[Monthly].subsequentPayment,")
            headingBuilder.append("Pricing[Monthly].totalPremium,")
            headingBuilder.append("Pricing[Monthly].underwritingResults,")
            headingBuilder.append("Pricing[Yearly].amountPayable,")
            headingBuilder.append("Pricing[Yearly].canBuyOnline,")
            headingBuilder.append("Pricing[Yearly].totalPremium,")
            headingBuilder.append("Pricing[Yearly].underwritingResults,")
            headingBuilder.append("Pricing.otherExcesses.STANDARD_EXCESS,")
            headingBuilder.append("Pricing.otherExcesses.WINDOW_GLASS,")
            headingBuilder.append("Pricing.otherExcesses.AGE_DRIVERS_UNDER_21,")
            headingBuilder.append("Pricing.otherExcesses.AGE_DRIVERS_21_24,")
            headingBuilder.append("Pricing.otherExcesses.INEXPERIENCED_DRIVER,")
            headingBuilder.append("Pricing.otherExcesses.UNLISTED_DRIVER,")
            headingBuilder.append("UnderwritingKnockOut,")
            //headingBuilder.append("applicationStatus,")
            headingBuilder.append("ApplicationStartDateTime,")
            headingBuilder.append("ApplicationLastModifiedDateTime,")
            //headingBuilder.append("lastPricingDate,")
            headingBuilder.append("PaymentType,")
            headingBuilder.append("InsuredValueDetails.agreedValue,")
            headingBuilder.append("InsuredValueDetails.marketOrAgreedValue,")
            headingBuilder.append("HasReachedQuoteStage,")
            headingBuilder.append("HasEmailedQuote,")
            headingBuilder.append("HasProgressedPastQuoteStage,")
            headingBuilder.append("HasReachedSaleStage")
            return headingBuilder.toString()
        }

        fun above90PartyHeading(): String {
            var headingBuilder: StringBuilder = java.lang.StringBuilder()
            headingBuilder.append("Application ID,")
            headingBuilder.append("Party.title,")
            headingBuilder.append("Party.dateOfBirth,")
            headingBuilder.append("Party.lastName,")
            headingBuilder.append("Party.firstName,")
            headingBuilder.append("Party.qffNumber,")
            headingBuilder.append("Party.iLoyalMemberId,")
            headingBuilder.append("Party.contactDetails.emailAddress,")
            headingBuilder.append("Party.contactDetails.mobileNumber,")
            headingBuilder.append("Party.drivingHistory.claims,")
            headingBuilder.append("Party.gender,")
            headingBuilder.append("Party.roles,")
            headingBuilder.append("Party.retired,")
            headingBuilder.append("Party.ownsHome,")
            headingBuilder.append("Party.ownsAnotherCar")
            return headingBuilder.toString()
        }
    }

    fun toBelow90AppCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("\"$applicationId\", ")
        csvString.append("\"${this.attributes?.campaign?.offerId?:""}\",")
        csvString.append("\"${this.attributes?.policy?.riskAddress?.postcode?:""}\",")
        csvString.append("\"${this.attributes?.policy?.riskAddress?.state?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.parkingMethod?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.redbookId?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.registrationNumber?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.use?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.vehicleColour?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.vehicleCondition?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.vehicleFinanceType?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.annualMileage?:""}\",")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.accessories)}\",")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.factoryOptions)}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.coverType?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.expiryDate?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.hasPreviousInsurance?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.noInsuranceReason?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.noInsuranceReasonDescription?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.companyName?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.purchaseDetails?.isRecentPurchase?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.purchaseDetails?.purchaseDate?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.purchaseDetails?.purchasePrice?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.ageRestriction?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.makeDescription?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.fuel?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.transmission?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.body?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.modelDescription?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.registrationYear?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.marketValue?:""}\",")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.bodyModifications)}) \",")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.otherModifications)}) \",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.additionalExcessCode?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.choiceOfRepairer?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.commencementDate?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.coverType?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.hireCarOption?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.instalmentPlanNumber?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.noClaimsDiscount?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.roadsideOption?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.stampDutyExemption?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.windscreenOption?:""}\",")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.ncdProtection?:""}\",")
        //csvString.append("\"${this.salesChannel?:""}\",")
        csvString.append("\"${this.attributes?.policy?.insuranceProduct?.productCode?:""""""}\",")
        csvString.append("\"${this.attributes?.policy?.insuranceProduct?.productGroup?:""}\",")
        appendPricingFields(csvString, false)
        appendPricingExcessFields(csvString)
        appendUnderWritingKnockout(csvString)
       // csvString.append("\"${this.applicationStatus?:""}\",")
        csvString.append("\"${this.createdTimestamp?:""}\",")
        csvString.append("\"${this.lastUpdatedTimestamp?:""}\",")
       // csvString.append("\"${this.lastPricingRequestedTimestamp?:""}\",")
        csvString.append("\"${this.paymentType?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.insuredValueDetails?.agreedValue?:""}\",")
        csvString.append("\"${this.attributes?.policy?.car?.insuredValueDetails?.marketOrAgreedValue?:""}\",")
        appendApplicationStages(csvString)
        csvString.append("\n")
        return csvString.toString()
    }

    fun toBelow90PartyCsvString(): String {
        var csvString = StringBuilder()
        //csvString.append("$applicationId, \n")
        this.attributes?.parties?.stream()?.forEach {
            csvString.append("\"$applicationId\", ")
            csvString.append("\"${it.title?:""}\", ")
            csvString.append("\"${it.dob?:""}\", ")
            csvString.append("\"${it.lastName?:""}\", ")
            csvString.append("\"${it.firstName?:""}\", ")
            csvString.append("\"${it.ownsAnotherCar?:""}\", ")
            csvString.append("\"${it.ownsHome?:""}\", ")
            csvString.append("\"${it.relationshipToPolicyHolder?:""}\", ")
            csvString.append("\"${it.relationshipToRegularDriver?:""}\", ")
            csvString.append("\"${it.qffNumber?:""}\", ")
            csvString.append("\"${it.memberId?:""}\", ")
            csvString.append("\"${it.contactDetails?.emailAddress?:""}\", ")
            csvString.append("\"${it.contactDetails?.mobileNumber?:""}\", ")
            csvString.append("\"${CollectionUtils.isNotEmpty(it.claims)}\", ")
            csvString.append("\"${it.licence?.licenceType?:""}\", ")
            csvString.append("\"${it.licence?.date?:""}\", ")
            csvString.append("\"${it.licence?.country?:""}\", ")
            csvString.append("\"${it.livesAtPolicyHolderAddress?:""}\", ")
            csvString.append("\"${it.gender?:""}\", ")
            csvString.append("\"${it.retired?:""}\", ")
            csvString.append("\"${it.authorisedToPolicy?:""}\", ")
            csvString.append("\"${it.roles?.joinToString(",")?:""}\"")
            csvString.appendln()
        }
        return csvString.toString()
    }
    fun toAbove90PartyCsvString(): String {
        var csvString = StringBuilder()
        //csvString.append("$applicationId, \n")
        this.attributes?.parties?.stream()
                //?.filter { it.roles != null && CollectionUtils.containsAny(it.roles, arrayOf("POLICY_HOLDER", "REGULAR_DRIVER")) }
                ?.forEach {
                    csvString.append("\"$applicationId\", ")
                    csvString.append("\"${it.title?:""}\", ")
                    csvString.append("\"${it.dob?:""}\", ")
                    csvString.append("\"${it.lastName?:""}\", ")
                    csvString.append("\"${it.firstName?:""}\", ")
                    csvString.append("\"${it.qffNumber?:""}\", ")
                    csvString.append("\"${it.memberId?:""}\", ")
                    csvString.append("\"${it.contactDetails?.emailAddress?:""}\", ")
                    csvString.append("\"${it.contactDetails?.mobileNumber?:""}\", ")
                    csvString.append("\"${CollectionUtils.isNotEmpty(it.claims)?:""}\", ")
                    csvString.append("\"${it.gender?:""}\", ")
                    csvString.append("\"${it.roles?.joinToString(",")?:""}\"")
                    csvString.append("\"${it.retired?:""}\", ")
                    csvString.append("\"${it.ownsHome?:""}\", ")
                    csvString.append("\"${it.ownsAnotherCar?:""}\", ")
                    csvString.appendln()
                }
        return csvString.toString()
    }

    fun toAbove90AppCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("\"$applicationId\", ")
        csvString.append("\"${this.attributes?.campaign?.offerId?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.registrationNumber?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.use?:""}\", ")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.accessories)?:""}\", ")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.factoryOptions)?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.companyName?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.previousInsurance?.expiryDate?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.purchaseDetails?.purchasePrice?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.purchaseDetails?.purchaseDate?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.makeDescription?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.modelDescription?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.body?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.registrationYear?:""}\", ")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.bodyModifications)?:""}\", ")
        csvString.append("\"${!CollectionUtils.isEmpty(attributes?.policy?.car?.otherModifications)?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.car?.vehicleFinanceType?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.additionalExcessCode?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.choiceOfRepairer?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.commencementDate?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.coverType?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.hireCarOption?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.instalmentPlanNumber?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.noClaimsDiscount?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.roadsideOption?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.stampDutyExemption?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.windscreenOption?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.policyDetails?.ncdProtection?:""}\", ")
        // csvString.append("\"${this.salesChannel?:""}\", ")
        // csvString.append("\"${this.startChannel?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.insuranceProduct?.productCode?:""}\", ")
        csvString.append("\"${this.attributes?.policy?.insuranceProduct?.productGroup?:""}\", ")
        appendPricingFields(csvString, true)
        appendUnderWritingKnockout(csvString)
        // csvString.append("\"${this.applicationStatus?:""}\", ")
        csvString.append("\"${this.createdTimestamp?:""}\", ")
        csvString.append("\"${this.lastUpdatedTimestamp?:""}\", ")
        // csvString.append("\"${this.lastPricingRequestedTimestamp?:""}\", ")
        appendDriverCount(csvString)
        csvString.append("\"${this.paymentType?:""}\", ")
        appendApplicationStages(csvString)
        csvString.append("\n")
        return csvString.toString()
        return ""
    }

    private fun appendUnderWritingKnockout(csvString: StringBuilder) {
        csvString.append(" ,")
        //TODO("not implemented") //
        //Comment in files says  - Populate this to true if either of convictions, suspensions , cancellations and maxdemerits is true
        //Need to know for which party role?
    }

    private fun appendApplicationStages(csvString: StringBuilder) {
        csvString.append("\"${attributes?.webEvents?.pages?.quoteReview?:""}\", ") // hasReachedQuoteStage
        csvString.append("\"${attributes?.webEvents?.pages?.emailedQuote?:""}\", ") // hasEmailedQuote
        csvString.append("\"${attributes?.webEvents?.pages?.startPurchase?:""}\", ") // hasProgressedPastQuotestage
        csvString.append("\"${attributes?.webEvents?.pages?.purchaseReview?:""}\", ") // hasReachedSaleStage
    }

    private fun appendPricingExcessFields(csvString: StringBuilder) {
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.STANDARD_EXCESS.toString()) }
                ?.amount?:""}\", ")
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.WINDOW_GLASS.toString()) }
                ?.amount?:""}\", ")
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.AGE_DRIVERS_UNDER_21.toString()) }
                ?.amount?:""}\", ")
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.AGE_DRIVERS_21_24.toString()) }
                ?.amount?:""}\", ")
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.INEXPERIENCED_DRIVER.toString()) }
                ?.amount?:""}\", ")
        csvString.append("\"${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.UNLISTED_DRIVER.toString()) }
                ?.amount?:""}\", ")
    }

    private fun appendPricingFields(csvString: StringBuilder, above90: Boolean) {
        val monthlyPricing = getPricing(InstalmentPlanNumber.MONTHLY_PAYMENT)
        val yearlyPricing = getPricing(InstalmentPlanNumber.ANNUAL_PAYMENT)
        if (above90) {
            csvString.append("\"${monthlyPricing?.canBuyOnline?:""}\", ")
            csvString.append("\"${monthlyPricing?.totalPremium?:""}\", ")
            csvString.append("\"${monthlyPricing?.underwritingResult?:""}\", ")
            csvString.append("\"${yearlyPricing?.canBuyOnline?:""}\", ")
            csvString.append("\"${yearlyPricing?.totalPremium?:""}\", ")
            csvString.append("\"${yearlyPricing?.underwritingResult?:""}\", ")
        }else{
            csvString.append("\"${monthlyPricing?.amountPayable?:""}\", ")
            csvString.append("\"${monthlyPricing?.canBuyOnline?:""}\", ")
            csvString.append("\"${monthlyPricing?.firstPayment?:""}\", ")
            csvString.append("\"${monthlyPricing?.subsequentPayment?:""}\", ")
            csvString.append("\"${monthlyPricing?.totalPremium?:""}\", ")
            csvString.append("\"${monthlyPricing?.underwritingResult?:""}\", ")
            csvString.append("\"${yearlyPricing?.amountPayable?:""}\", ")
            csvString.append("\"${yearlyPricing?.canBuyOnline?:""}\", ")
            csvString.append("\"${yearlyPricing?.totalPremium?:""}\", ")
            csvString.append("\"${yearlyPricing?.underwritingResult?:""}\", ")
        }

    }

    private fun appendDriverCount(csvString: StringBuilder) {
        csvString.append("\""+(this.attributes?.parties?.stream()?.filter { it.livesAtPolicyHolderAddress != null && it.livesAtPolicyHolderAddress!! }?.count())+"\",")
        csvString.append("\""+this.attributes?.parties?.count()?.or(0)+"\",")
    }


    fun getPricing(instalmentPlanNumber: InstalmentPlanNumber): Price? {
        return this.pricing?.details?.prices
                ?.find { StringUtils.equals(instalmentPlanNumber.toString(), it.instalmentPlanNumber) }
    }
}

data class ApplicationAttributes(
        var parties: MutableList<Party>? = null,
        var campaign: Campaign? = null,
        var salesChannel: String? = null,
        var policy: Policy? = null,
        var webEvents: WebEvents?=null) {
}

data class WebEvents(
        var initialReferrer: String? = null,
        var quoteUrl: String? = null,
        var uiVariantId: String? = null,
        var pages: WebEventPages? = null) {
}

data class WebEventPages(
        var quoteReview: Boolean? = null,
        var startPurchase: Boolean? = null,
        var emailedQuote: Boolean? = null,
        var purchaseReview: Boolean? = null
) {}

data class Campaign(var offerId: String? = null) {}

enum class InstalmentPlanNumber { MONTHLY_PAYMENT, ANNUAL_PAYMENT }