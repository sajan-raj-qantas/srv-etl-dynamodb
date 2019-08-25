package com.qantasloyalty.lsl.etlservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

data class ApplicationDataNew(

        var applicationId: String? = null,
        @JsonIgnore
        var attributes: ApplicationAttributes? = null,
        @JsonIgnore
        var pricing: Pricing? = null,
        var salesChannel: String? = null, // from Application
        var startChannel: String? = null, //TODO find out source
        var applicationStatus: String? = null, // from Application
        var createdTimestamp: String? = null, // from Application
        var lastUpdatedTimestamp: String? = null, // from Application
        var lastPricingRequestedTimestamp: String? = null, // from Application
        var paymentType: String? = null,
        var pricingEmailedTimestamp: String? = null

) {
    fun toBelow90AppCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("$applicationId, ")
        csvString.append("${this.attributes?.campaign?.offerId}, ")
        csvString.append("${this.attributes?.policy?.riskAddress?.postcode}, ")
        csvString.append("${this.attributes?.policy?.riskAddress?.state}, ")
        csvString.append("${this.attributes?.policy?.car?.parkingMethod}, ")
        csvString.append("${this.attributes?.policy?.car?.redbookId} , ")
        csvString.append("${this.attributes?.policy?.car?.registrationNumber} , ")
        csvString.append("${this.attributes?.policy?.car?.use} , ")
        csvString.append("${this.attributes?.policy?.car?.vehicleColour} , ")
        csvString.append("${this.attributes?.policy?.car?.vehicleCondition} , ")
        csvString.append("${this.attributes?.policy?.car?.vehicleFinanceType} , ")
        csvString.append("${this.attributes?.policy?.car?.annualMileage} , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.accessories)}) , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.factoryOptions)}) , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.coverType} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.expiryDate} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.hasPreviousInsurance} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.noInsuranceReason} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.noInsuranceReasonDescription} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.companyName} , ")
        csvString.append("${this.attributes?.policy?.car?.purchaseDetails?.isRecentPurchase} , ")
        csvString.append("${this.attributes?.policy?.car?.purchaseDetails?.purchaseDate} , ")
        csvString.append("${this.attributes?.policy?.car?.purchaseDetails?.purchasePrice} , ")
        csvString.append("${this.attributes?.policy?.car?.ageRestriction} , ")
        csvString.append("${this.attributes?.policy?.car?.make} , ")
        csvString.append("${this.attributes?.policy?.car?.fuel} , ")
        csvString.append("${this.attributes?.policy?.car?.transmission} , ")
        csvString.append("${this.attributes?.policy?.car?.body} , ")
        csvString.append("${this.attributes?.policy?.car?.model} , ")
        csvString.append("${this.attributes?.policy?.car?.registrationYear} , ")
        csvString.append("${this.attributes?.policy?.car?.marketValue} , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.bodyModifications)}) , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.otherModifications)}) , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.additionalExcessCode} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.choiceOfRepairer} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.commencementDate} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.coverType} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.hireCarOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.instalmentPlanNumber} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.noClaimsDiscount} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.roadsideOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.stampDutyExemption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.windscreenOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.ncdProtection} , ")
        csvString.append("${this.salesChannel} , ")
        csvString.append("${this.attributes?.policy?.insuranceProduct?.productCode} , ")
        csvString.append("${this.attributes?.policy?.insuranceProduct?.productGroup} , ")
        appendPricingFields(csvString, false)
        appendPricingExcessFields(csvString)
        appendUnderWritingKnockout(csvString)
        csvString.append("${this.applicationStatus}, ")
        csvString.append("${this.createdTimestamp}, ")
        csvString.append("${this.lastUpdatedTimestamp}, ")
        csvString.append("${this.lastPricingRequestedTimestamp}, ")
        csvString.append("${this.paymentType}, ")
        csvString.append("${this.attributes?.policy?.car?.insuredValueDetails?.agreedValue}, ")
        csvString.append("${this.attributes?.policy?.car?.insuredValueDetails?.marketOrAgreedValue}, ")
        appendApplicationStages(csvString)
        csvString.append("\n")
        return csvString.toString()
    }

    private fun appendUnderWritingKnockout(csvString: StringBuilder) {
        //TODO("not implemented") //
        //Comment in files says  - Populate this to true if either of convictions, suspensions , cancellations and maxdemerits is true
        //Need to know for which party role?
    }

    private fun appendApplicationStages(csvString: StringBuilder) {
        var hasReachedQuoteStage: Boolean? = null
        var hasEmailedQuote: Boolean? = null
        var hasProgressedPastQuoteStage: Boolean? = null
        var hasReachedSaleStage: Boolean? = null
        //TODO ("not implemented")
    }

    private fun appendPricingExcessFields(csvString: StringBuilder) {
        csvString.append("${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.STANDARD_EXCESS.toString()) }
                ?.amount}, ")
        csvString.append("${pricing?.details?.otherExcesses
                ?.find {StringUtils.equals(it.code, OtherExcessCode.WINDOW_GLASS.toString()) }
                ?.amount}, ")
        csvString.append("${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.AGE_DRIVERS_UNDER_21.toString()) }
                ?.amount}, ")
        csvString.append("${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.AGE_DRIVERS_21_24.toString()) }
                ?.amount}, ")
        csvString.append("${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.INEXPERIENCED_DRIVER.toString()) }
                ?.amount}, ")
        csvString.append("${pricing?.details?.otherExcesses
                ?.find { StringUtils.equals(it.code, OtherExcessCode.UNLISTED_DRIVER.toString()) }
                ?.amount}, ")
    }

    private fun appendPricingFields(csvString: StringBuilder, above90: Boolean) {
        val monthlyPricing = getPricing(InstalmentPlanNumber.MONTHLY_PAYMENT)
        val yearlyPricing = getPricing(InstalmentPlanNumber.ANNUAL_PAYMENT)
        if (above90) {
            csvString.append("${monthlyPricing?.amountPayable} , ")
            csvString.append("${monthlyPricing?.firstPayment} , ")
            csvString.append("${monthlyPricing?.subsequentPayment} , ")
            csvString.append("${yearlyPricing?.amountPayable} , ")
        }
        csvString.append("${monthlyPricing?.canBuyOnline} , ")
        csvString.append("${monthlyPricing?.totalPremium} , ")
        csvString.append("${monthlyPricing?.underwritingResult} , ")
        csvString.append("${yearlyPricing?.canBuyOnline} , ")
        csvString.append("${yearlyPricing?.totalPremium} , ")
        csvString.append("${yearlyPricing?.underwritingResult} , ")
    } fun toBelow90PartyCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("$applicationId, ")
        this.attributes?.parties?.stream()?.forEach {
            csvString.append("${it.title}, ")
            csvString.append("${it.dob}, ")
            csvString.append("${it.lastName}, ")
            csvString.append("${it.firstName}, ")
            csvString.append("${it.ownsAnotherCar}, ")
            csvString.append("${it.ownsHome}, ")
            csvString.append("${it.relationshipToPolicyHolder}, ")
            csvString.append("${it.relationshipToRegularDriver}, ")
            csvString.append("${it.qffNumber}, ")
            csvString.append("${it.contactDetails?.emailAddress}, ")
            csvString.append("${it.contactDetails?.mobileNumber}, ")
            csvString.append("${CollectionUtils.isNotEmpty(it.claims)}, ")
            csvString.append("${it.licence?.licenceType}, ")
            csvString.append("${it.licence?.date}, ")
            csvString.append("${it.licence?.country}, ")
            csvString.append("${it.livesAtPolicyHolderAddress}, ")
            csvString.append("${it.gender}, ")
            csvString.append("${it.retired}, ")
            csvString.append("${it.authorisedToPolicy}, ")
            csvString.append("${it.roles} ")
            csvString.append("\n")
        }
        return csvString.toString()
    } fun toAbove90AppCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("$applicationId, ")
        csvString.append("${this.attributes?.campaign?.offerId}, ")
        csvString.append("${this.attributes?.policy?.car?.registrationNumber} , ")
        csvString.append("${this.attributes?.policy?.car?.use} , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.accessories)}) , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.factoryOptions)}) , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.companyName} , ")
        csvString.append("${this.attributes?.policy?.car?.previousInsurance?.expiryDate} , ")
        csvString.append("${this.attributes?.policy?.car?.purchaseDetails?.purchasePrice} , ")
        csvString.append("${this.attributes?.policy?.car?.purchaseDetails?.purchaseDate} , ")
        csvString.append("${this.attributes?.policy?.car?.make} , ")
        csvString.append("${this.attributes?.policy?.car?.model} , ")
        csvString.append("${this.attributes?.policy?.car?.body} , ")
        csvString.append("${this.attributes?.policy?.car?.registrationYear} , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.bodyModifications)}) , ")
        csvString.append("${!CollectionUtils.isEmpty(attributes?.policy?.car?.otherModifications)}) , ")
        csvString.append("${this.attributes?.policy?.car?.vehicleFinanceType} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.additionalExcessCode} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.choiceOfRepairer} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.commencementDate} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.coverType} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.hireCarOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.instalmentPlanNumber} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.noClaimsDiscount} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.roadsideOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.stampDutyExemption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.windscreenOption} , ")
        csvString.append("${this.attributes?.policy?.policyDetails?.ncdProtection} , ")
        csvString.append("${this.salesChannel} , ")
        csvString.append("${this.startChannel} , ")
        csvString.append("${this.attributes?.policy?.insuranceProduct?.productCode} , ")
        csvString.append("${this.attributes?.policy?.insuranceProduct?.productGroup} , ")
        appendPricingFields(csvString, true)
        appendUnderWritingKnockout(csvString)
        csvString.append("${this.applicationStatus}, ")
        csvString.append("${this.createdTimestamp}, ")
        csvString.append("${this.lastUpdatedTimestamp}, ")
        csvString.append("${this.lastPricingRequestedTimestamp}, ")
        appendDriverCount(csvString)
        csvString.append("${this.paymentType}, ")
        appendApplicationStages(csvString)
        csvString.append("\n")
        return csvString.toString()
        return ""
    }

    private fun appendDriverCount(csvString: StringBuilder) {
        csvString.append(this.attributes?.parties?.stream()?.filter { it.livesAtPolicyHolderAddress!=null && it.livesAtPolicyHolderAddress!! }?.count())
        csvString.append(this.attributes?.parties?.count()?.or(0))
    }

    fun toAbove90PartyCsvString(): String {
        var csvString = StringBuilder()
        csvString.append("$applicationId, ")
        this.attributes?.parties?.stream()
                ?.filter{it.roles!=null && CollectionUtils.containsAny(it.roles, arrayOf("POLICY_HOLDER", "REGULAR_DRIVER"))}
                ?.forEach {
                            csvString.append("${it.title}, ")
                            csvString.append("${it.dob}, ")
                            csvString.append("${it.lastName}, ")
                            csvString.append("${it.firstName}, ")
                            csvString.append("${it.qffNumber}, ")
                            csvString.append("${it.memberId}, ")
                            csvString.append("${it.contactDetails?.emailAddress}, ")
                            csvString.append("${it.contactDetails?.mobileNumber}, ")
                            csvString.append("${CollectionUtils.isNotEmpty(it.claims)}, ")
                            csvString.append("${it.gender}, ")
                            csvString.append("${it.roles} ")
                            csvString.append("${it.retired}, ")
                            csvString.append("${it.ownsHome}, ")
                            csvString.append("${it.ownsAnotherCar}, ")
                            csvString.append("\n")
        }
        return csvString.toString()
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
        var policy: Policy? = null) {
}

data class Campaign(
        var offerId: String? = null) {
}

enum class InstalmentPlanNumber {
    MONTHLY_PAYMENT, ANNUAL_PAYMENT
}