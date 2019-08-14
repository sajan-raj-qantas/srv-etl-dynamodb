package com.qantasloyalty.lsl.etlservice.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import lombok.Builder
import lombok.Getter
import lombok.Setter
import java.time.LocalDate
import kotlin.reflect.KProperty

class AttributeValueDelegate(val map: Map<String, AttributeValue?>) {
    lateinit var value: Any
    operator fun <T> getValue(thisRef: ApplicationDataWithDelegate, property: KProperty<*>): T {
        if (::value.isInitialized) {
            return value as T
        }

        map[property.name]?.let {
            value = it.s ?: (it.n ?: it.b)
        }

        return value as T
    }

}

@Getter
@Setter
@Builder
data class ApplicationDataWithDelegate(val map: Map<String, AttributeValue?>) {

    val applicationId: String by AttributeValueDelegate(map)
    val riskAddressPostcode: String by AttributeValueDelegate(map)
    val riskAddressState: String by AttributeValueDelegate(map)
    val carParkingMethod: String by AttributeValueDelegate(map)
    val carRedBookID: String by AttributeValueDelegate(map)
    val carRegistrationNumber: String by AttributeValueDelegate(map)
    val carUse: String by AttributeValueDelegate(map)
    val carVehicleColour: String by AttributeValueDelegate(map)
    val carVehicleCondition: String by AttributeValueDelegate(map)
    val carVehicleFinanceType: String by AttributeValueDelegate(map)
    val carAnnualMileage: String by AttributeValueDelegate(map)
    val carAccessories: Boolean by AttributeValueDelegate(map)
    val carFactoryOptions: Boolean by AttributeValueDelegate(map)
    val carPreviousInsuranceCoverType: String by AttributeValueDelegate(map)
    val carPreviousInsuranceExpiryDate: LocalDate by AttributeValueDelegate(map)
    val carPreviousInsuranceHasPreviousInsurance: Boolean by AttributeValueDelegate(map)
    val carNoInsuranceReason: String by AttributeValueDelegate(map)
    val carPurchaseDetailsIsRecentPurchase: Boolean by AttributeValueDelegate(map)
    val carMake: String by AttributeValueDelegate(map)
    val carModel: String by AttributeValueDelegate(map)
    val carRegistrationYear: String by AttributeValueDelegate(map)
    val carBodyModifications: Boolean by AttributeValueDelegate(map)
    val carOtherModiciations: Boolean by AttributeValueDelegate(map)
    val policyDetailsAdditionalExcessCode: String by AttributeValueDelegate(map)
    val policyDetailsChoiceOfRepairer: Boolean by AttributeValueDelegate(map)
    val policyDetailsCommencementDate: LocalDate by AttributeValueDelegate(map)
    val policyDetailsCoverType: String by AttributeValueDelegate(map)
    val policyDetailsHireCarOption: Boolean by AttributeValueDelegate(map)
    val policyDetailsInstalmentplanNumber: String by AttributeValueDelegate(map)
    val policyDetailsNoClaimsDiscount: Boolean by AttributeValueDelegate(map)
    val policyDetailsRoadsideOption: Boolean by AttributeValueDelegate(map)
    val policyDetailsStampDutyExemption: Boolean by AttributeValueDelegate(map)
    val policyDetailsWindscreenOption: Boolean by AttributeValueDelegate(map)
    val policyDetailsNcdProtection: Boolean by AttributeValueDelegate(map)
    val salesChannel: String by AttributeValueDelegate(map)
    val InsuranceProductProductCode: String by AttributeValueDelegate(map)
    val InsuranceProductProductGroup: String by AttributeValueDelegate(map)
    val pricingMonthlyAmountPayable: String by AttributeValueDelegate(map)
    val pricingMonthlyCanBuyOnline: String by AttributeValueDelegate(map)
    val pricingMonthlyFirstPayment: Double by AttributeValueDelegate(map)
    val pricingMonthlySubsequentPayment: Double by AttributeValueDelegate(map)
    val pricingMonthlyTotalPremium: Double by AttributeValueDelegate(map)
    val pricingMonthlyUnderwritingResults: String by AttributeValueDelegate(map)
    val pricingYearlyAmountPayable: Double by AttributeValueDelegate(map)
    val pricingYearlyCanBuyOnline: Boolean by AttributeValueDelegate(map)
    val pricingYearlyTotalPremium: Double by AttributeValueDelegate(map)
    val pricingYearlyUnderwritingResults: String by AttributeValueDelegate(map)
    val suspensions: String by AttributeValueDelegate(map)
    val convictions: String by AttributeValueDelegate(map)
    val endorsements: String by AttributeValueDelegate(map)
    val cancellations: String by AttributeValueDelegate(map)
    val applicationStatus: String by AttributeValueDelegate(map)
    val applicationStartDate: LocalDate by AttributeValueDelegate(map)
    val applicationLastModifiedDate: LocalDate by AttributeValueDelegate(map)
    val lastPricingDate: LocalDate by AttributeValueDelegate(map)
    val paymentType: String by AttributeValueDelegate(map)

}
