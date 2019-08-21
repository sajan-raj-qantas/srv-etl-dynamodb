package com.qantasloyalty.lsl.etlservice.model

import com.fasterxml.jackson.annotation.JsonProperty


class Pricing {
    private var pricingId: String? = null
    private var details: PriceDetails? = null
}

 class PriceDetails {
    private var partnerReferenceId: String? = null
    private var leadNumber: String? = null
    private var prices: MutableList<Price>? = null
    private var availableExcesses: MutableList<AvailableExcess>? = null
    private var otherExcesses: MutableList<OtherExcess>? = null
}
 class Price {
    private var amountPayable: Double? = null
    private var canBuyOnline: Boolean? = null
    private var discountDetails: DiscountDetails? = null
    private var emergencyServicesLevy: Double? = null
    private var firstPayment: Double? = null
    private var goodsAndServicesTax: Double? = null
    private var instalmentPlanNumber: String? = null
    private var instalments: Int? = null
    private var paymentDescription: String? = null
    private var policyTerm: String? = null
    private var stampDuty: Double? = null
    private var subsequentPayment: Double? = null
    private var taxPremium: Double? = null
    private var totalAmount: Double? = null
    private var totalPremium: Double? = null
    private var totalPremiumWithoutTax: Double? = null
    private var underwritingResult: String? = null
}

public class AvailableExcess {
    private var amount: Int? = null
    private var code: String? = null
}
public class OtherExcess {
    private var amount: Int? = null
    private var code: String? = null
}

public class DiscountDetails {
    private var discountApplied: Boolean? = null
    private var discountPercentage: String? = null
    private var savedSum: Int? = null
}
