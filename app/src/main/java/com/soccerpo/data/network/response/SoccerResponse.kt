package com.soccerpo.data.network.response

import com.soccerpo.data.db.entity.Soccer
import com.soccerpo.data.db.entity.SoccerChinese

data class SoccerResponse(
    val isActive: Boolean?,
    val en: List<Soccer>?,
    val zh: List<Soccer>?
)
