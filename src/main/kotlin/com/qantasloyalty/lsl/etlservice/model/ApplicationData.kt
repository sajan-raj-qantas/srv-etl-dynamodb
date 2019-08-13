package com.qantasloyalty.lsl.etlservice.model

import lombok.Builder
import lombok.Getter
import lombok.Setter
import java.time.LocalDate

@Getter
@Setter
@Builder
data class ApplicationData(val map: Map<String, Any?>) {

    val applicationId: String by map
    val riskAddressPostcode: String by map
    val riskAddressState: String by map
    val carParkingMethod: String by map
    val carRedBookID: String by map
    val carRegistrationNumber: String by map
    val carUse: String by map
    val carVehicleColour: String by map
    val carVehicleCondition: String by map
    val carVehicleFinanceType: String by map
    val carAnnualMileage: String by map
    val carAccessories: Boolean by map
    val carFactoryOptions: Boolean by map
    val carPreviousInsuranceCoverType: String by map
    val carPreviousInsuranceExpiryDate: LocalDate by map
    val carPreviousInsuranceHasPreviousInsurance: Boolean by map
    val carNoInsuranceReason: String by map
    val carPurchaseDetailsIsRecentPurchase: Boolean by map
    val carMake: String by map
    val carModel: String by map
    val carRegistrationYear: String by map
    val carBodyModifications: Boolean by map
    val carOtherModiciations: Boolean by map
    val policyDetailsAdditionalExcessCode: String by map
    val policyDetailsChoiceOfRepairer: Boolean by map
    val policyDetailsCommencementDate: LocalDate by map
    val policyDetailsCoverType: String by map
    val policyDetailsHireCarOption: Boolean by map
    val policyDetailsInstalmentplanNumber: String by map
    val policyDetailsNoClaimsDiscount: Boolean by map
    val policyDetailsRoadsideOption: Boolean by map
    val policyDetailsStampDutyExemption: Boolean by map
    val policyDetailsWindscreenOption: Boolean by map
    val policyDetailsNcdProtection: Boolean by map
    val salesChannel: String by map
    val InsuranceProductProductCode: String by map
    val InsuranceProductProductGroup: String by map
    val pricingMonthlyAmountPayable: String by map
    val pricingMonthlyCanBuyOnline: String by map
    val pricingMonthlyFirstPayment: Double by map
    val pricingMonthlySubsequentPayment: Double by map
    val pricingMonthlyTotalPremium: Double by map
    val pricingMonthlyUnderwritingResults: String by map
    val pricingYearlyAmountPayable: Double by map
    val pricingYearlyCanBuyOnline: Boolean by map
    val pricingYearlyTotalPremium: Double by map
    val pricingYearlyUnderwritingResults: String by map
    val suspensions: String by map
    val convictions: String by map
    val endorsements: String by map
    val cancellations: String by map
    val applicationStatus: String by map
    val applicationStartDate: LocalDate by map
    val applicationLastModifiedDate: LocalDate by map
    val lastPricingDate: LocalDate by map
    val paymentType: String by map

}
