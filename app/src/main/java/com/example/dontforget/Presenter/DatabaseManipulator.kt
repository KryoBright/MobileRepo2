package com.example.dontforget

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

class DatabaseManipulator(var context : Context,var parentActivity:InterfaceActivityLauncher?,val pref:SharedPreferences) {

    var db=AppDatabase.getAppDataBase(context)

    fun register(userName:String,userEmail:String,userPassword:String){
        val sPEditor=pref.edit()
        var usr=UserEntity()
        usr.email=userEmail
        usr.user_name=userName
        usr.password=userPassword
        var retrofitResponse=RetrofitManipulatorClass.GetTokenReg(usr).body()
        usr.user_token=retrofitResponse!!.api_token
        usr.id= retrofitResponse.id.toLong()
        db?.UserDAO()?.insertUser(usr)
        var intent= Intent(context,ListOfNotes::class.java)
        sPEditor.putString("email",usr.email)
        sPEditor.putString("token",usr.user_token)
        sPEditor.apply()
        parentActivity!!.launchActivity(intent)
        parentActivity=null
    }

    fun login(userEmail:String,userPassword:String)
    {
        if (db!!.UserDAO().getUserByEmail(userEmail).isNotEmpty())
        {

            val sPEditor=pref.edit()
            var usr=db!!.UserDAO().getUserByEmail(userEmail)[0]
            usr.user_token=RetrofitManipulatorClass.GetTokenLog(usr).body()!!.api_token
            db!!.UserDAO().insertUser(usr)
            var intent= Intent(context,ListOfNotes::class.java)
            sPEditor.putString("email",usr.email)
            sPEditor.putString("token",usr.user_token)
            sPEditor.apply()
            parentActivity!!.launchActivity(intent)
            parentActivity=null
        }
    }

    fun getCategories():List<CategoryEntity>
    {
        return db!!.CategoryDAO().getCategories()
    }

    fun getTasks(category:Long):List<TaskEntity>
    {
        return db!!.TaskDAO().getTasks()
    }

    fun taskAmount():Int
    {
        return db!!.TaskDAO().getTasks().size
    }

    fun updStatus(status:Boolean,taskid:Long)
    {
        val task=db!!.TaskDAO().getTaskByID(taskid)[0]
        task.task_done=status
        db!!.TaskDAO().insertTask(task)
    }

    fun redact(task_id:Long)
    {

        val sPEditor=pref.edit()
        var intent= Intent(context,NewOrRedactActivity::class.java)
        sPEditor.putLong("taskid",task_id)
        sPEditor.apply()
        parentActivity!!.launchActivity(intent)
        parentActivity=null
    }

    fun newTask()
    {

    }

    fun getPriorities():List<PriorityEntity>
    {
        return db!!.PriorityDAO().getPriorities()
    }

    fun updTask(task_id: Long,user_email: String, priority_id: Long,category_id: Long,task_title: String?,task_description:String?,task_done:Boolean,task_deadline:Long)
    {
        var tsk=TaskEntity()
        tsk.task_done=task_done
        tsk.category_id=category_id
        tsk.priority_id=priority_id
        tsk.task_deadline=task_deadline
        tsk.task_description=task_description
        if (task_id.toInt()!=-1) {
            tsk.task_id = task_id
        }
        else{/*
            var tmp=TaskSend()
            tmp.category_id=category_id.toInt()
            tmp.deadline=task_deadline.toInt()
            tmp.description=task_description
            if (task_done)
            {
                tmp.done=1
            }
            else{
                tmp.done=0
            }
            tmp.priority_id=priority_id.toInt()
            tmp.title=task_title
            tsk.task_id=RetrofitManipulatorClass.makeTask(pref.getString("token",""),tmp).id.toLong()*/
            var i=0
            getCategories().forEach { i+=getTasks(it.category_id).size }
            tsk.task_id=i.toLong()
        }
        tsk.task_title=task_title
        tsk.user_id=db!!.UserDAO().getUserByEmail(user_email)[0].id
        db!!.TaskDAO().insertTask(tsk)
    }

    fun addCategory(categoryName:String)
    {
        var cat=CategoryEntity()
        cat.category_title=categoryName
        cat.category_id=db!!.CategoryDAO().getCategories().size.toLong()
        cat.color="#343434"
        db!!.CategoryDAO().insertCategory(cat)
    }

    fun syncronize(token:String,user_id:Int)
    {
        var tmp=RetrofitManipulatorClass.service.getPriorities("Bearer "+token).execute()
        if ((tmp.isSuccessful)and(!tmp.body().isNullOrEmpty()))
        tmp.body()!!.forEach {
            var tmpInner=PriorityEntity()
            tmpInner.priority_color=it.color
            tmpInner.priority_title=it.name
            tmpInner.priority_id=it.id.toLong()
            db!!.PriorityDAO().insertPriority(tmpInner)
        }
        var tmp2=RetrofitManipulatorClass.service.getCategories("Bearer "+token).execute()
        if ((tmp2.isSuccessful)and(!tmp2.body().isNullOrEmpty()))
        tmp2.body()!!.forEach {
            var tmpInner=CategoryEntity()
            tmpInner.category_id=it.id.toLong()
            tmpInner.category_title=it.name
            tmpInner.color=it.color
            tmpInner.user_id=user_id
            db!!.CategoryDAO().insertCategory(tmpInner)
        }
        //getTasksToken(token)
        AdapterForRecycler.prepare(this)
    }

    fun getIdFromEmail(email:String):Long{
        return db!!.UserDAO().getUserByEmail(email)[0].id
    }

    fun getTasksToken(token:String){
        var a=RetrofitManipulatorClass.service.getTasks(token).execute().body()
        a?.forEach {
            updTask(it.id.toLong(),pref.getString("email",""),it.priority!!.id.toLong(),it.category!!.id.toLong(),it.title,it.description,it.done.equals(1),it.deadline.toLong())
        }
    }

    fun taskById(id:Long):TaskEntity{
        return db!!.TaskDAO().getTasks()[id.toInt()]
    }
}