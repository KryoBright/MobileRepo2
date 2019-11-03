package com.example.dontforget

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import retrofit2.Response

class RetrofitManipulatorClass{
    companion object {
        lateinit var retrofit : Retrofit
        lateinit var service : InterfaceAPI
        fun CreateRetrofit() {
            retrofit = Retrofit.Builder()
                .baseUrl("http://practice.mobile.kreosoft.ru/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
            service = retrofit.create(InterfaceAPI::class.java)
        }

        fun GetTokenLog(userSome:UserEntity):Response<Token>  {
            val a=service.userLog(userSome.email,userSome.password).execute()
            return a
        }

        fun GetTokenReg(userSome:UserEntity):Response<RegisterUser>{
            val a=service.userReg(userSome.email,userSome.user_name,userSome.password).execute()
            return a
        }

        fun GetPriorities(token:String):Response<Array<PriorityData>>{

            return service.getPriorities(token).execute()
        }

        fun makeTask(token:String,taskSend : TaskSend):TaskData
        {
            return service.createTask(token,taskSend).execute().body()!!
        }
    }
}