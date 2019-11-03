package com.example.dontforget

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.toColorInt
import kotlinx.android.synthetic.main.activity_task_more.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityTaskMore : AppCompatActivity(),InterfaceActivityLauncher {
    override fun launchActivity(intent : Intent) {
        startActivity(intent)
    }
    lateinit var task:TaskEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_more)
        val pref=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (pref.contains("id"))
        {
            val newDataManip=DatabaseManipulator(this,this,pref)
            var i=0

            GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    task=newDataManip.taskById(pref.getLong("id",0))
                    i=1
                }
            }
            while (i==0) {
            }
            TextMoreName.text=task.task_title
            TextMoreDescription.text=task.task_description
            TextMoreCategory.text=task.category_id.toString()
            TextMorePriority.text=task.priority_id.toString()
            TextMoreDeadline.text=task.task_deadline.toString()
        }

        ButtonNext.setOnClickListener {
            launchActivity(Intent(this,NewOrRedactActivity::class.java))
        }
    }
}
