package com.qantasloyalty.lsl.etlservice.model

data class PartyBelow90Data(
        var applicationId: String?,
        var partyTitle: String?,
        var partyDateOfBirth: String?,
        var partyLastName: String?,
        var partyFirstName: String?,
        var partyOwnsAnotherCar: Boolean?,
        var partyOwnsHome: Boolean?,
        var partyRelationshipToPolicyHolder: String?,
        var partyRelationshipToRegularDriver: String?,
        var partyQffNumber: String?,
        var partyILoyalMemberId: String?,
        var partyEmailAddress: String?,
        var partyMobileNumber: String?,
        var partyDrivingHistoryClaims: String?,
        var partyLicenceType: String?,
        var partyLicenceDate: String?,
        var partyLicenceCountry: String?,
        var partyLivesAtPolicyHolderAddress: Boolean?,
        var partyGender: String?,
        var partyRetired: Boolean?,
        var partyAuthorisedToPolicy: Boolean?,
        var partyRoles: String?
) {}

