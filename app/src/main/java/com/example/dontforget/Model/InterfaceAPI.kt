package com.example.dontforget

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface InterfaceAPI {
    @POST("register")
    fun userReg(@Query("email")email:String?,@Query("name")userName:String?,@Query("password")userPassword:String? ): Call<RegisterUser>

    @POST("login")
    fun userLog(@Query("email")email:String?,@Query("password")userPassword:String? ):Call<Token>


    @GET("priorities")
    fun getPriorities(@Header("Authorization")token:String):Call<Array<PriorityData>>

    @GET("categories")
    fun getCategories(@Header("Authorization")token:String):Call<Array<CategoryData>>

    @POST("categories")
    fun createCategory(@Header("api_token")token:String):Call<CategoryData>

    @GET("tasks")
    fun getTasks(@Header("api_token")token:String):Call<List<TaskData>>

    @POST("taskss")
    fun createTask(@Header("api_token")token:String,@Body task:TaskSend):Call<TaskData>

    @PATCH("tasks/{id}")
    fun updTask(id:Int):Call<TaskData>

    @DELETE("tasks/{id}")
    fun delTask(id: Int):Call<Response<Any>>
}