package com.daedongyeojido.daedongyeojido_v1.home

data class BannerData(
    val id: Long,
    val bannerImgUrl: String
)

data class HomeClubData(
    val clubName: String,
    val title: String?,
    val clubImageUrl: String?,
    val tags: List<String>
)

//data class HomeAnnouncementData(
//    val title: String,
//    val contents: String
//)

data class HomeClubResponse(
    val banners: List<BannerData>,
    val allClubResponses: List<HomeClubData>,
    // val announcement: List<HomeAnnouncementData>
)