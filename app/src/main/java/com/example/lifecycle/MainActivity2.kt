package com.example.lifecycle

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lifecycle.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    var firstName = ""
    var lastName  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val userData = intent
        if(userData != null){
            firstName = userData?.getStringExtra("user_f_name").toString()
            lastName  = userData?.getStringExtra("user_l_name").toString()

            findViewById<TextView>(R.id.display_user_first_name)?.text = firstName;
            findViewById<TextView>(R.id.display_user_last_name )?.text = lastName;

            findViewById<TextView>(R.id.display_user_first_name).setText(firstName);
            findViewById<TextView>(R.id.display_user_last_name ).setText(lastName)
        }
    }

}