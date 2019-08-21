package com.qantasloyalty.lsl.etlservice.model

data class Party (
     var sequenceNumber: Int? = null,
     var title: String? = null,
     var firstName: String? = null,
     var gender: String? = null,
     var hasConvictions: Boolean? = null,
     var lastName: String? = null,
     var livesAtPolicyHolderAddress: Boolean? = null,
     var ownsAnotherCar: Boolean? = null,
     var ownsHome: Boolean? = null,
     var relationshipToPolicyHolder: String? = null,
     var relationshipToRegularDriver: String? = null,
     var cancellations: MutableList<Cancellation>? = null,
     var claims: MutableList<Claim>? = null,
     var qffNumber: String? = null,
     var memberId: String? = null,
     var companyName: String? = null,
     var contactDetails: PartyContactDetails? = null,
     var convictions: MutableList<Conviction>? = null,
     var dob: String? = null,
     var drivingHistory: PartyDrivingHistory? = null,
     var emailDocuments: Boolean? = null,
     var retired: Boolean? = null,
     var licence: PartyLicence? = null,
     var roles: MutableList<String>? = null,
     var authorisedToPolicy: Boolean? = null){

}

data class Cancellation(
     var company: String? = null,
     var date: String? = null,
     var reason: String? = null){

}

data class PartyContactDetails(
     var emailAddress: String? = null,
     var workNumber: String? = null,
     var mobileNumber: String? = null,
     var homeNumber: String? = null){
}

data class Claim(
     var accidentDriver: String? = null,
     var accidentDriverSequenceNumber: String? = null,
     var amount: Int? = null,
     var claimLoss: String? = null,
     var claimType: String? = null,
     var damageLevel: String? = null,
     var date: String? = null,
     var guiltyParty: String? = null){

}

data class Conviction (
     var date: String? = null,
     var reason: String? = null){
}

data class PartyDrivingHistory (
     var cancellations: Boolean? = null,
     var maxDemerits: Boolean? = null,
     var endorsements: Boolean? = null,
     var claims: String? = null,
     var suspensions: Boolean? = null,
     var losses: Boolean? = null){
}

data class PartyLicence (
     var date: String? = null,
     var country: String? = null,
     var licenceType: String? = null){
}

