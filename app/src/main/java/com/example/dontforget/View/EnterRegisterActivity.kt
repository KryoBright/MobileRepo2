package com.example.dontforget

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.enter_register_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnterRegisterActivity : AppCompatActivity(),InterfaceActivityLauncher {

    override fun launchActivity(intent : Intent) {
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_register_layout)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.INTERNET, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }

        var state=true
        var pref=getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (pref.contains("email"))
        {
            var intent= Intent(this,ListOfNotes::class.java)
            startActivity(intent)
            finish()
        }
        RetrofitManipulatorClass.CreateRetrofit()

        buttonForward.setOnClickListener{
            val someContext:Context=this
            GlobalScope.launch {
                withContext(Dispatchers.Default)
                {
                    var newDataManip = DatabaseManipulator(someContext,this@EnterRegisterActivity,pref)
                    if (state) {
                        newDataManip.register(
                            NameTextView.text.toString(),
                            UserEmailTextView.text.toString(),
                            PasswordTextView.text.toString()
                        )
                    }
                    else {
                        newDataManip.login(UserEmailTextView.text.toString(), PasswordTextView.text.toString())
                    }
                }
            }

        }

        buttonChangeState.setOnClickListener{
            if (state)
            {
                TitleTextView.text="Вход"
                NameTextView.visibility= View.GONE
                RPasswordTextView.visibility=View.GONE
                buttonForward.text="Вход"
                buttonChangeState.text="Регистрация"
            }
            else
            {
                TitleTextView.text="Регистрация"
                NameTextView.visibility= View.VISIBLE
                RPasswordTextView.visibility=View.VISIBLE
                buttonForward.text="Регистрация"
                buttonChangeState.text="Вход"
            }
            state=!state

        }

    }
}
