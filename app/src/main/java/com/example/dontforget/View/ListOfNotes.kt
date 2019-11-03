package com.example.dontforget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_list_of_notes.*
import kotlinx.android.synthetic.main.content_list_of_notes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListOfNotes : AppCompatActivity(),InterfaceActivityLauncher
{
    override fun launchActivity(intent : Intent) {
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_notes)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            var intent= Intent(this,NewOrRedactActivity::class.java)
            startActivity(intent)
        }

        var pref=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        Toast.makeText(this,pref.getString("token","0"),Toast.LENGTH_SHORT).show()

        RetrofitManipulatorClass.CreateRetrofit()
        var con: Context =this
        var act: ListOfNotes=this
        var i=1
        GlobalScope.launch {
            withContext(Dispatchers.Default)
            {
                i=0
                var newDataManip = DatabaseManipulator(con,act,pref)
                newDataManip.syncronize(pref.getString("token","0")!!,newDataManip.getIdFromEmail(pref.getString("email","0")!!).toInt())
                i=1
            }
        }
        while (i==0)
        {
        }
        RecyclerForTasks.layoutManager = LinearLayoutManager(con)
        RecyclerForTasks.adapter = AdapterForRecycler(con,this,pref)
        /*
        var newDataManip = DatabaseManipulator(this,this)

        RecyclerForTasks.layoutManager=LinearLayoutManager(this)
        RecyclerForTasks.adapter=AdapterForRecycler(this)*/
    }

    override fun onResume() {
        super.onResume()
        RecyclerForTasks.adapter?.notifyDataSetChanged()
    }
}
