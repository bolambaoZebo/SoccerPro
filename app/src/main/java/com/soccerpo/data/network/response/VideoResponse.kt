package com.soccerpo.data.network.response

import com.soccerpo.data.db.entity.Videos


data class VideoResponse(
    val isActive: Boolean?,
    val data: List<Videos>
)

