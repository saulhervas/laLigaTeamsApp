package com.saulhervas.laligateamsapp.utils


import com.google.gson.annotations.SerializedName

data class TeamsDataResponse(
    @SerializedName("get") val get: String,
    @SerializedName("response") val responseTeam: List<Response>
)

data class Response(

    @SerializedName("team") var team: Team,
    @SerializedName("venue") var venue: Venue

)

data class Team(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("founded") var founded: Int? = null,
    @SerializedName("national") var national: Boolean? = null,
    @SerializedName("logo") var logo: String? = null

)

data class Venue(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("capacity") var capacity: Int? = null,
    @SerializedName("surface") var surface: String? = null,
    @SerializedName("image") var image: String? = null

)