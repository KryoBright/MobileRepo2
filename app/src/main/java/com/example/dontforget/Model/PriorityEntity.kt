package com.example.dontforget

import androidx.core.graphics.toColorInt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PriorityEntity {

    @PrimaryKey
    var priority_id: Long = 0

    var priority_title: String? = null

    var priority_color: String?= null
}