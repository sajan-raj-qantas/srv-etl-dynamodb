package com.qantasloyalty.lsl.etlservice.model


class Pricing {
    var pricingId: String? = null
    var details: PriceDetails? = null
}

class PriceDetails {
    var partnerReferenceId: String? = null
    var leadNumber: String? = null
    var prices: MutableList<Price>? = null
    var availableExcesses: MutableList<AvailableExcess>? = null
    var otherExcesses: MutableList<OtherExcess>? = null
}

class Price {
    var amountPayable: Double? = null
    var canBuyOnline: Boolean? = null
    var discountDetails: DiscountDetails? = null
    var emergencyServicesLevy: Double? = null
    var firstPayment: Double? = null
    var goodsAndServicesTax: Double? = null
    var instalmentPlanNumber: String? = null
    var instalments: Int? = null
    var paymentDescription: String? = null
    var policyTerm: String? = null
    var stampDuty: Double? = null
    var subsequentPayment: Double? = null
    var taxPremium: Double? = null
    var totalAmount: Double? = null
    var totalPremium: Double? = null
    var totalPremiumWithoutTax: Double? = null
    var underwritingResult: String? = null
}

class AvailableExcess {
    var amount: Int? = null
    var code: String? = null
}

class OtherExcess {
    var amount: Int? = null
    var code: String? = null
}

class DiscountDetails {
    var discountApplied: Boolean? = null
    var discountPercentage: String? = null
    var savedSum: Int? = null
}

enum class OtherExcessCode {
    STANDARD_EXCESS,
    AGE_DRIVERS_21_24,
    AGE_DRIVERS_UNDER_21,
    UNLISTED_DRIVER,
    INEXPERIENCED_DRIVER,
    WINDOW_GLASS,
    MALE_DRIVERS,
    NON_RETIRED_DRIVERS,
    LOW_KILOMETRE_EXCESS
}
