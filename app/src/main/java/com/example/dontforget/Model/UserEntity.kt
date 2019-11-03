package com.example.dontforget

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class UserEntity {

    @PrimaryKey
    var id: Long = 0

    var user_name: String? = null

    var email:String?=null

    var password:String?=null

    var user_token:String?=null
}