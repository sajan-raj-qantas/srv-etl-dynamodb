package com.qantasloyalty.lsl.etlservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import org.apache.commons.lang3.StringUtils
import org.springframework.util.CollectionUtils
import java.io.StringWriter

data class ApplicationDataNew (

    var applicationId: String? = null,
    @JsonIgnore
    var attributes: ApplicationAttributes? =null,
    @JsonIgnore
    var pricing: Pricing?=null,
    var salesChannel: String? = null, // from Application
    var status: String? = null, // from Application
    var createdTimestamp: String? = null, // from Application
    var lastUpdatedTimestamp: String? = null, // from Application
    var lastPricingRequestedTimestamp: String? = null, // from Application
    var paymentType: String? = null){ // To confirm)

    /*fun toCsv():String {
        var writer  = StringWriter()
        var csvWriter = CSVWriter(writer)
        var beanToCsv = StatefulBeanToCsvBuilder<ApplicationDataNew>(writer).build()
        beanToCsv.write(this)
        writer.close()
        return writer.toString()
    }
*/

    fun toCsvString(): String {
        return "$applicationId, " +
                "${this.attributes?.policy?.riskAddress?.postcode}, " +
                "${this.attributes?.policy?.riskAddress?.state}, " +
                "${this.attributes?.policy?.car?.parkingMethod}, " +
                "${this.attributes?.policy?.car?.redbookId} , " +
                "${this.attributes?.policy?.car?.registrationNumber} , " +
                "${this.attributes?.policy?.car?.use} , " +
                "${this.attributes?.policy?.car?.vehicleColour} , " +
                "${this.attributes?.policy?.car?.vehicleCondition} , " +
                "${this.attributes?.policy?.car?.vehicleFinanceType} , " +
                "${this.attributes?.policy?.car?.annualMileage} , " +
                "${!CollectionUtils.isEmpty(attributes?.policy?.car?.accessories)}) , " +
                "${!CollectionUtils.isEmpty(attributes?.policy?.car?.factoryOptions)}) , " +
                "${this.attributes?.policy?.car?.previousInsurance?.coverType} , " +
                "${this.attributes?.policy?.car?.previousInsurance?.expiryDate} , " +
                "${this.attributes?.policy?.car?.previousInsurance?.hasPreviousInsurance} , " +
                "${this.attributes?.policy?.car?.previousInsurance?.noInsuranceReason} , " +
                "${this.attributes?.policy?.car?.purchaseDetails?.isRecentPurchase} , " +
                "${this.attributes?.policy?.car?.make} , " +
                "${this.attributes?.policy?.car?.model} , " +
                "${this.attributes?.policy?.car?.registrationYear} , " +
                "${this.attributes?.policy?.car?.modified} , " +
                "${this.attributes?.policy?.car?.otherModifications} , " +
                "${!CollectionUtils.isEmpty(attributes?.policy?.car?.otherModifications)}) , " +
                "${this.attributes?.policy?.policyDetails?.additionalExcessCode} , " +
                "${this.attributes?.policy?.policyDetails?.choiceOfRepairer} , " +
                "${this.attributes?.policy?.policyDetails?.commencementDate} , " +
                "${this.attributes?.policy?.policyDetails?.coverType} , " +
                "${this.attributes?.policy?.policyDetails?.hireCarOption} , " +
                "${this.attributes?.policy?.policyDetails?.instalmentPlanNumber} , " +
                "${this.attributes?.policy?.policyDetails?.noClaimsDiscount} , " +
                "${this.attributes?.policy?.policyDetails?.roadsideOption} , " +
                "${this.attributes?.policy?.policyDetails?.stampDutyExemption} , " +
                "${this.attributes?.policy?.policyDetails?.windscreenOption} , " +
                "${this.attributes?.policy?.policyDetails?.ncdProtection} , " +
                "$salesChannel, " +
                "${this.attributes?.policy?.insuranceProduct?.productCode} , " +
                "${this.attributes?.policy?.insuranceProduct?.productGroup} , " +
                /*"$pricingMonthlyAmountPayable, " +
                "$pricingMonthlyCanBuyOnline, " +
                "$pricingMonthlyFirstPayment, " +
                "$pricingMonthlySubsequentPayment, " +
                "$pricingMonthlyTotalPremium, " +
                "$pricingMonthlyUnderwritingResults, " +
                "$pricingYearlyAmountPayable, " +
                "$pricingYearlyCanBuyOnline, " +
                "$pricingYearlyTotalPremium, " +
                "$pricingYearlyUnderwritingResults, " +
                "$underwritingKnockOut, " +
                "$applicationStatus, " + */
                "${this.createdTimestamp}, " +
                "${this.lastUpdatedTimestamp}, " +
                "${this.lastPricingRequestedTimestamp}, " +
                "${this.paymentType}"
    }
}

data class ApplicationAttributes (
     var parties: MutableList<Party>? = null,
     var campaign: Campaign? = null,
     var salesChannel: String? = null,
     var policy: Policy? = null){

}

data class Campaign (
    private var offerId: String? = null){
}





