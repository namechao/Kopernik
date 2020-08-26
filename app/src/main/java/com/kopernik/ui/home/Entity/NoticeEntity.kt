package com.kopernik.ui.home.Entity

data class NoticeEntity(
    val notice: List<Notice>
)

data class Notice(
    val content: String,
    val createTime: Long,
    val id: Int,
    val img: Any,
    val noticeAbstract: String,
    val title: String,
    val url: Any
)