package com.daedongyeojido.daedongyeojido_v1.club

data class ClubDetailResponse(
    val clubName: String,
    val title: String,
    val introduction: String,
    val project: String,
    val weWant: String,
    val clubImageUrl: String,
    val tags: List<String>,
    val clubMembers: List<ClubMember>,
    val questResponses: List<QnAData>,
    val qandA: String
)

data class ClubMember(
    val name: String
)
data class ClubInfo(
    val introduction: String,
    val project: String,
    val weWant: String,
    val tags: List<String>,
    val clubMembers: List<ClubMember>
)
data class QnAData(
    val questionId: Int,
    val question: String,
    val answer: String
)

data class NoticeData(
    val id: Long,
    val major: String,
    val clubName: String,
    val deadline: String,
    val applyOrNot: Boolean
)