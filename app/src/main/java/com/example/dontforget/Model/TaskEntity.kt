package com.example.dontforget

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity
class TaskEntity {

    @PrimaryKey
    var task_id: Long = 0

    var user_id: Long = 0

    var priority_id: Long = 0

    var category_id: Long = 0

    var task_title: String? = null

    var task_description:String?=null

    var task_done:Boolean=false

    var task_deadline:Long=0
}