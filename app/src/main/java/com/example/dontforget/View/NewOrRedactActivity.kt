package com.example.dontforget

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_new_or_redact.*
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.android.synthetic.main.note_item.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class NewOrRedactActivity : AppCompatActivity(),InterfaceActivityLauncher {
    override fun launchActivity(intent : Intent) {
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_or_redact)
        var thiscont: Context =this
        var thisact:NewOrRedactActivity=this
        val pref=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        GlobalScope.launch {
            withContext(Dispatchers.Default)
            {
                RetrofitManipulatorClass.CreateRetrofit()
                var newDataManip = DatabaseManipulator(thiscont,thisact,pref)

                val asArr= newDataManip.getCategories().map{ it->it.category_title}.toTypedArray()
                val adapter = ArrayAdapter(thiscont, android.R.layout.simple_spinner_item,asArr)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = adapter

                var priorities= newDataManip.getPriorities().map { it->it.priority_title }
                if (priorities.isEmpty())
                {
                    priorities= arrayOf("1","2","3").asList()
                }
                val adapter2=ArrayAdapter(thiscont, android.R.layout.simple_spinner_item,priorities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                prioritySpinner.adapter = adapter2
            }
        }


        buttonAddCategory.setOnClickListener{
            var text=""
            var alertDialogBldr= AlertDialog.Builder(this)
            alertDialogBldr.setTitle("Enter name of file")
            val inflater = layoutInflater
            alertDialogBldr.setTitle("Enter name of file")
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_layout, null)
            alertDialogBldr.setView(dialogLayout)
            alertDialogBldr.setPositiveButton("FINISHED") { dialog, which ->
                text=dialogLayout.editText.text.toString()
            }
            alertDialogBldr.show()
            GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    RetrofitManipulatorClass.CreateRetrofit()
                    var newDataManip = DatabaseManipulator(thiscont, thisact, pref)
                    newDataManip.addCategory(text)
                }
            }
            Toast.makeText(this,"Dialog category",Toast.LENGTH_LONG).show()
        }

        buttonSave.setOnClickListener{
            GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    var newDataManip = DatabaseManipulator(thiscont,thisact,pref)
                    newDataManip.updTask(pref.getLong("id",-1),pref.getString("email","@mail.ru")!!,prioritySpinner.selectedItemId,
                        categorySpinner.selectedItemId,taskRedactNameText.text.toString(),textRedDesk.text.toString(),false,deadlineRedactText.text.toString().toLong())
                    pref.edit().remove("id").apply()
                }
            }
            var intent= Intent(this,ListOfNotes::class.java)
            startActivity(intent)
        }
    }
}
