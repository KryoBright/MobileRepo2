package com.example.dontforget

data class UserData(
    var api_token:String?=null,
    var name:String?=null,
    var password:String?=null,
    var id:Int=0,
    var email:String?=null
)

data class PriorityData(
    var id:Int=0,
    var name:String?=null,
    var color:String?=null
)

data class CategoryData(
    var id:Int=0,
    var color : String?=null,
    var name: String?=null
)

data class TaskData(
    var id: Int=0,
    var title:String?=null,
    var description:String?=null,
    var done:Int=0,
    var deadline:Int=0,
    var category:CategoryData?=null,
    var priority:PriorityData?=null,
    var created:Int=0
)


data class  TaskSend(
    var title:String?=null,
    var description:String?=null,
    var done:Int=0,
    var deadline:Int=0,
    var category_id:Int=0,
    var priority_id:Int=0
)
data class RegisterUser(
    var email:String?=null,
    var name:String?=null,
    var id:Int=0,
    var api_token:String?=null
)

data class Token(
    var api_token : String?=null
)