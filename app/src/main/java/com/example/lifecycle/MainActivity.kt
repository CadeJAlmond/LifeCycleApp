package com.example.lifecycle

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var userFirstN = ""
    private var userMidN   = ""
    private var userLastN  = ""
    private var userPic = false;
    var takenImage : Bitmap? = null
    private var fnInputBox : EditText? = null
    private var lnInputBox : EditText? = null
    private var mnInputBox : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fnInputBox = findViewById(R.id.user_first_n)
        lnInputBox = findViewById(R.id.user_last_n)
        mnInputBox = findViewById(R.id.user_mid_n)

        findViewById<Button>(R.id.take_photo).setOnClickListener() {
            if (validInputs()) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val REQUEST_CODE = 33
                startActivityForResult(takePictureIntent, REQUEST_CODE)

            } else {
                Toast.makeText(
                    this, "You must enter first, last and middle names",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        findViewById<Button>(R.id.next_page).setOnClickListener(){
            if(userPic)
                loginUser()
            else
                Toast.makeText(this, "Please take a picture", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCode && resultCode == RESULT_OK){
            takenImage = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.display_user_img)?.setImageBitmap(takenImage)

            fnInputBox?.setEnabled(false)
            mnInputBox?.setEnabled(false)
            lnInputBox?.setEnabled(false)

            userPic = true

            userFirstN = fnInputBox?.text.toString()
            userLastN  = lnInputBox?.text.toString()
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }

    fun validInputs(): Boolean {
        if(!TextUtils.isEmpty(fnInputBox?.text.toString()))
            if(!TextUtils.isEmpty(mnInputBox?.text.toString()))
                if(!TextUtils.isEmpty(lnInputBox?.text.toString()))
                    return true;
        return false;
    }

    fun loginUser(){
        val loginAct = Intent(this, MainActivity2::class.java)
        val userData = Bundle()
        loginAct.putExtra("user_f_name", userFirstN)
        loginAct.putExtra("user_l_name", userLastN)
        loginAct.putExtras(userData)
        startActivity(loginAct)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        userFirstN = savedInstanceState.getString("savedFirstN", "").toString()
        userMidN  = savedInstanceState.getString("savedMidN", "" ).toString()
        userLastN = savedInstanceState.getString("savedLastN", "").toString()
        userPic   = savedInstanceState.getBoolean("userPic")
        fnInputBox?.setText(userFirstN)
        mnInputBox?.setText(userMidN)
        lnInputBox?.setText(userLastN)

        Toast.makeText(this, (userPic).toString(), Toast.LENGTH_LONG).show()
        if(userPic){
            takenImage = savedInstanceState.getParcelable("img", Bitmap::class.java)
            Toast.makeText(this, (takenImage == null).toString(), Toast.LENGTH_LONG).show()
            findViewById<ImageView>(R.id.display_user_img)?.setImageBitmap(takenImage)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        val userFirstName =  findViewById<EditText>(R.id.user_first_n)?.text.toString()
        val userLastName  =  findViewById<EditText>(R.id.user_last_n )?.text.toString()
        val userMidName   =  findViewById<EditText>(R.id.user_mid_n  )?.text.toString()
        outState.putString("savedFirstN", userFirstName)
        outState.putString("savedMidN",  userMidName  )
        outState.putString("savedLastN", userLastName )
        outState.putBoolean("userPic", userPic)
        if(userPic)
            outState.putParcelable("img", takenImage)
        super.onSaveInstanceState(outState)
    }
}