package com.daedongyeojido.daedongyeojido_v1.user


data class MypageResponse(
    val classNumber: String,
    val name: String,
    val part: String,
    val myClub: String,
    val myReport: List<ReportData>
)

data class ReportData(
    val id: Int,
    val clubName: String,
    val hopeMajor: String,
    val deadline: String,
    val passingResult: String
)