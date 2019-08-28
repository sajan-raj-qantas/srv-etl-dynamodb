package com.qantasloyalty.lsl.etlservice.mapper

import com.qantasloyalty.lsl.etlservice.model.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class ApplicationDataMapper {

    fun toAppAbove90(applicationData: ApplicationData): ApplicationAbove90Data {

        val applicationAbove90Data = ApplicationAbove90Data(
                applicationId = applicationData.applicationId,
                offerId = applicationData?.attributes?.campaign?.offerId,
                registrationNumber = applicationData?.attributes?.policy?.car?.registrationNumber,
                use = applicationData?.attributes?.policy?.car?.use,
                accessories = !CollectionUtils.isEmpty(applicationData?.attributes?.policy?.car?.accessories),
                factoryOptions = !CollectionUtils.isEmpty(applicationData?.attributes?.policy?.car?.factoryOptions),
                previouInsuranceCompanyName = applicationData?.attributes?.policy?.car?.previousInsurance?.companyName,
                previousInsuranceExpiryDate = applicationData?.attributes?.policy?.car?.previousInsurance?.expiryDate,
                purchaseDetailsPurchasePrice = applicationData?.attributes?.policy?.car?.purchaseDetails?.purchasePrice,
                purchaseDetailsPurchaseDate = applicationData?.attributes?.policy?.car?.purchaseDetails?.purchaseDate,
                make = applicationData?.attributes?.policy?.car?.makeDescription,
                model = applicationData?.attributes?.policy?.car?.modelDescription,
                body = applicationData?.attributes?.policy?.car?.body,
                registrationYear = applicationData?.attributes?.policy?.car?.registrationYear,
                bodyModifications = !CollectionUtils.isEmpty(applicationData?.attributes?.policy?.car?.bodyModifications),
                otherModiciations = !CollectionUtils.isEmpty(applicationData?.attributes?.policy?.car?.otherModifications),
                vehicleFinanceType = applicationData?.attributes?.policy?.car?.vehicleFinanceType,
                additionalExcessCode = applicationData?.attributes?.policy?.policyDetails?.additionalExcessCode,
                choiceOfRepairer = applicationData?.attributes?.policy?.policyDetails?.choiceOfRepairer,
                commencementDate = applicationData?.attributes?.policy?.policyDetails?.commencementDate,
                coverType = applicationData?.attributes?.policy?.policyDetails?.coverType,
                hireCarOption = applicationData?.attributes?.policy?.policyDetails?.hireCarOption,
                instalmentPlanNumber = applicationData?.attributes?.policy?.policyDetails?.instalmentPlanNumber,
                noClaimsDiscount = applicationData?.attributes?.policy?.policyDetails?.noClaimsDiscount?.type,
                roadsideOption = applicationData?.attributes?.policy?.policyDetails?.roadsideOption,
                stampDutyExemption = applicationData?.attributes?.policy?.policyDetails?.stampDutyExemption,
                windscreenOption = applicationData?.attributes?.policy?.policyDetails?.windscreenOption,
                ncdProtection = applicationData?.attributes?.policy?.policyDetails?.ncdProtection,
                insuranceProductProductCode = applicationData?.attributes?.policy?.insuranceProduct?.productCode,
                insuranceProductProductGroup = applicationData?.attributes?.policy?.insuranceProduct?.productGroup,
                applicationStartDate = applicationData?.createdTimestamp,
                applicationLastModifiedDate = applicationData?.lastUpdatedTimestamp,
                paymentType = applicationData?.paymentType
        )
        setPricingFields(applicationData, applicationAbove90Data)
        setUnderWritingKnockout(applicationData, applicationAbove90Data)
        setDriverCount(applicationData, applicationAbove90Data)
        return applicationAbove90Data
    }

    fun toAppBelow90(applicationData: ApplicationData): ApplicationBelow90Data {

        val applicationAbove90Data = ApplicationBelow90Data(
                applicationId = applicationData.applicationId,
                offerId = applicationData.attributes?.campaign?.offerId,
                riskAddressPostcode = applicationData?.attributes?.policy?.riskAddress?.postcode,
                riskAddressState = applicationData?.attributes?.policy?.riskAddress?.state,
                parkingMethod = applicationData.attributes?.policy?.car?.parkingMethod,
                redbookId = applicationData.attributes?.policy?.car?.redbookId,
                registrationNumber = applicationData.attributes?.policy?.car?.registrationNumber,
                carUse = applicationData.attributes?.policy?.car?.use,
                vehicleColour = applicationData.attributes?.policy?.car?.vehicleColour,
                vehicleCondition = applicationData.attributes?.policy?.car?.vehicleCondition,
                vehicleFinanceType = applicationData.attributes?.policy?.car?.vehicleFinanceType,
                annualMileage = applicationData.attributes?.policy?.car?.annualMileage,
                accessories = !CollectionUtils.isEmpty(applicationData.attributes?.policy?.car?.accessories),
                factoryOptions = !CollectionUtils.isEmpty(applicationData.attributes?.policy?.car?.factoryOptions),
                previousInsuranceCoverType = applicationData.attributes?.policy?.car?.previousInsurance?.coverType,
                previousInsuranceExpiryDate = applicationData.attributes?.policy?.car?.previousInsurance?.expiryDate,
                hasPreviousInsurance = applicationData.attributes?.policy?.car?.previousInsurance?.hasPreviousInsurance,
                noInsuranceReason = applicationData.attributes?.policy?.car?.previousInsurance?.noInsuranceReason,
                noInsuranceReasonDescription = applicationData.attributes?.policy?.car?.previousInsurance?.noInsuranceReasonDescription,
                previousInsuranceCompanyName = applicationData.attributes?.policy?.car?.previousInsurance?.companyName,
                carIsRecentPurchase = applicationData.attributes?.policy?.car?.purchaseDetails?.isRecentPurchase,
                carPurchaseDate = applicationData.attributes?.policy?.car?.purchaseDetails?.purchaseDate,
                carPurchasePrice = applicationData.attributes?.policy?.car?.purchaseDetails?.purchasePrice?.toString(),
                carAgeRestriction = applicationData.attributes?.policy?.car?.ageRestriction,
                carMake = applicationData.attributes?.policy?.car?.makeDescription,
                carFuel = applicationData.attributes?.policy?.car?.fuel,
                carTransmission = applicationData.attributes?.policy?.car?.transmission,
                carBody = applicationData.attributes?.policy?.car?.body,
                carModel = applicationData.attributes?.policy?.car?.modelDescription,
                carRegistrationYear = applicationData.attributes?.policy?.car?.registrationYear,
                carMarketValue = applicationData.attributes?.policy?.car?.marketValue,
                carBodyModifications = !CollectionUtils.isEmpty(applicationData.attributes?.policy?.car?.bodyModifications),
                carOtherModifications = !CollectionUtils.isEmpty(applicationData.attributes?.policy?.car?.otherModifications),
                policyDetailsAdditionalExcessCode = applicationData.attributes?.policy?.policyDetails?.additionalExcessCode,
                policyDetailsChoiceOfRepairer = applicationData.attributes?.policy?.policyDetails?.choiceOfRepairer,
                policyDetailsCommencementDate = applicationData.attributes?.policy?.policyDetails?.commencementDate,
                policyDetailsCoverType = applicationData.attributes?.policy?.policyDetails?.coverType,
                policyDetailsHireCarOption = applicationData.attributes?.policy?.policyDetails?.hireCarOption,
                policyDetailsInstalmentPlanNumber = applicationData.attributes?.policy?.policyDetails?.instalmentPlanNumber,
                policyDetailsNoClaimsDiscount = applicationData.attributes?.policy?.policyDetails?.noClaimsDiscount,
                policyDetailsRoadsideOption = applicationData.attributes?.policy?.policyDetails?.roadsideOption,
                policyDetailsWindscreenOption = applicationData.attributes?.policy?.policyDetails?.windscreenOption,
                policyDetailsNcdProtection = applicationData.attributes?.policy?.policyDetails?.ncdProtection,
                policyDetailsStampDutyExemption = applicationData.attributes?.policy?.policyDetails?.stampDutyExemption,
                insuranceProductProductCode = applicationData?.attributes?.policy?.insuranceProduct?.productCode,
                insuranceProductProductGroup = applicationData?.attributes?.policy?.insuranceProduct?.productGroup,
                applicationStartDateTime = applicationData?.createdTimestamp,
                applicationLastModifiedDateTime = applicationData?.lastUpdatedTimestamp,
                paymentType = applicationData.paymentType,
                insuredValueDetailsAgreedValue = applicationData.attributes?.policy?.car?.insuredValueDetails?.agreedValue,
                insuredValueDetailsMarketOrAgreedValue = applicationData.attributes?.policy?.car?.insuredValueDetails?.marketOrAgreedValue
        )
        setPricingFields(applicationData, applicationAbove90Data)
        setUnderWritingKnockout(applicationData, applicationAbove90Data)
        return applicationAbove90Data
    }

    fun toPartyBelow90(applicationData: ApplicationData): List<PartyBelow90Data>? {
        return applicationData.attributes?.parties?.stream()?.map {
            PartyBelow90Data(
                    applicationData.applicationId,
                    it.title,
                    it.dob,
                    it.lastName,
                    it.firstName,
                    it.ownsAnotherCar,
                    it.ownsHome,
                    it.relationshipToPolicyHolder,
                    it.relationshipToRegularDriver,
                    it.qffNumber,
                    it.memberId,
                    it.contactDetails?.emailAddress,
                    it.contactDetails?.mobileNumber,
                    it.drivingHistory?.claims,
                    it.licence?.licenceType,
                    it.licence?.date,
                    it.licence?.country,
                    it.livesAtPolicyHolderAddress,
                    it.gender,
                    it.retired,
                    it.authorisedToPolicy,
                    it.roles?.joinToString(";")
            )
        }?.collect(Collectors.toList())
    }

    fun toPartyAbove90(applicationData: ApplicationData): List<PartyAbove90Data>? {
        return applicationData.attributes?.parties?.stream()?.map {
            PartyAbove90Data(
                    applicationData.applicationId,
                    it.title,
                    it.dob,
                    it.lastName,
                    it.firstName,
                    it.qffNumber,
                    it.memberId,
                    it.contactDetails?.emailAddress,
                    it.contactDetails?.mobileNumber,
                    it.drivingHistory?.claims,
                    it.gender,
                    it.roles?.joinToString(";"),
                    it.retired,
                    it.ownsHome,
                    it.ownsAnotherCar
            )
        }?.collect(Collectors.toList())
    }

    private fun setDriverCount(applicationData: ApplicationData, applicationAbove90Data: ApplicationAbove90Data) {
        applicationAbove90Data.totalNoOfHouseHoldDrivers =
                applicationData.attributes?.parties?.stream()
                        ?.filter { it.livesAtPolicyHolderAddress != null && it.livesAtPolicyHolderAddress!! }?.count()?.toInt()

        applicationAbove90Data.totalDrivers = applicationData.attributes?.parties?.count()
    }

    private fun setUnderWritingKnockout(applicationData: ApplicationData, applicationAbove90Data: ApplicationAbove90Data) {
        applicationAbove90Data.knockout = (applicationData.attributes?.other?.cancellations ?: false
                .or(applicationData.attributes?.other?.suspensions ?: false)
                .or(applicationData.attributes?.other?.convictions ?: false)
                .or(applicationData.attributes?.other?.maxDemerits ?: false))
    }

    private fun setUnderWritingKnockout(applicationData: ApplicationData, applicationBelow90Data: ApplicationBelow90Data) {
        applicationBelow90Data.underwritingKnockOut = (applicationData.attributes?.other?.cancellations ?: false
                .or(applicationData.attributes?.other?.suspensions ?: false)
                .or(applicationData.attributes?.other?.convictions ?: false)
                .or(applicationData.attributes?.other?.maxDemerits ?: false))
    }

    private fun setPricingFields(applicationData: ApplicationData, applicationAbove90Data: ApplicationAbove90Data) {
        val monthlyPricing = getPricing(applicationData.pricing?.details?.prices, InstalmentPlanNumber.MONTHLY_PAYMENT)
        applicationAbove90Data.pricingMonthlyCanBuyOnline = monthlyPricing?.canBuyOnline
        applicationAbove90Data.pricingMonthlyTotalPremium = monthlyPricing?.totalPremium?.toString()
        applicationAbove90Data.pricingMonthlyUnderwritingResults = monthlyPricing?.underwritingResult
        val yearlyPricing = getPricing(applicationData.pricing?.details?.prices, InstalmentPlanNumber.ANNUAL_PAYMENT)
        applicationAbove90Data.pricingYearlyCanBuyOnline = yearlyPricing?.canBuyOnline
        applicationAbove90Data.pricingYearlyTotalPremium = yearlyPricing?.totalPremium?.toString()
        applicationAbove90Data.pricingYearlyUnderwritingResults = yearlyPricing?.underwritingResult
    }

    private fun setPricingFields(applicationData: ApplicationData, applicationBelow90Data: ApplicationBelow90Data) {
        val monthlyPricing = getPricing(applicationData.pricing?.details?.prices, InstalmentPlanNumber.MONTHLY_PAYMENT)
        applicationBelow90Data.pricingMonthlyCanBuyOnline = monthlyPricing?.canBuyOnline
        applicationBelow90Data.pricingMonthlyTotalPremium = monthlyPricing?.totalPremium?.toString()
        applicationBelow90Data.pricingMonthlyUnderwritingResults = monthlyPricing?.underwritingResult
        val yearlyPricing = getPricing(applicationData.pricing?.details?.prices, InstalmentPlanNumber.ANNUAL_PAYMENT)
        applicationBelow90Data.pricingYearlyCanBuyOnline = yearlyPricing?.canBuyOnline
        applicationBelow90Data.pricingYearlyTotalPremium = yearlyPricing?.totalPremium?.toString()
        applicationBelow90Data.pricingYearlyUnderwritingResults = yearlyPricing?.underwritingResult
    }

    private fun getPricing(prices: MutableList<Price>?, instalmentPlanNumber: InstalmentPlanNumber?): Price? {
        return prices
                ?.find { StringUtils.equals(instalmentPlanNumber.toString(), it.instalmentPlanNumber) }
    }
}


