package com.example.dontforget

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class CategoryEntity {

    @PrimaryKey
    var category_id: Long = 0

    var category_title: String? = null

    var color:String?=null

    var user_id:Int=0
}