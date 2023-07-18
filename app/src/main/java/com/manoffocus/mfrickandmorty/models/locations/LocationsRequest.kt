package com.manoffocus.mfrickandmorty.models.locations

data class LocationsRequest(
    var info: Info? = null,
    var results: List<MFLocation>
)