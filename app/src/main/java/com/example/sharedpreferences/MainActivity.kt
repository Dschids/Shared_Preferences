package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    // lateinit widgets
    lateinit var user_name: EditText
    lateinit var password: EditText
    lateinit var dl_switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        /* set up theme selection before setcontent view, so it has this info when it loads
        used getString for file name, I tried using "@string/my_set_file" but it was crashing the app
         */
        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        val use_dark_theme = SP.getBoolean("dark_theme", false)
        if (use_dark_theme){
            setTheme(androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dark)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // link to widgets
        user_name = findViewById(R.id.etUserName)
        password = findViewById(R.id.etPass)
        dl_switch = findViewById(R.id.swDarkLight)

        dl_switch.isChecked = use_dark_theme
        // run a function that refreshes the screen when you click the dark/light switch
        dl_switch.setOnCheckedChangeListener {
            view, isChecked -> toggleTheme(isChecked)
        }

    }

    override fun onResume() {
        super.onResume()

        // open up the sharedpreferences file
        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        // get our strings and boolean values from shared preferences file
        var name_key = SP.getString("name", "")
        var pass_key = SP.getString("pass", "")
        var theme_key = SP.getBoolean("dark_theme", false)

        // set the widgets to display the values from shared preferences
        user_name!!.setText(name_key)
        password!!.setText(pass_key)
        dl_switch.isChecked = theme_key

    }

    override fun onPause() {
        super.onPause()

        // open shared preferences and set it so we can edit
        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        var myEdit = SP.edit()

        // edit the strings and booleans in shared preferences to contain the data from widgets
        myEdit.apply {
            putString("name", user_name!!.text.toString())
            putString("pass", password!!.text.toString())
            putBoolean("dark_theme", dl_switch.isChecked)
            // commit the changes to shared preferences
            commit()
        }


    }

    private fun toggleTheme(darkTheme: Boolean){
        // open shared preferences editor, all in one line
        var editor = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE).edit()

        // save changes to the dark theme boolean from the dark/light switch
        editor.apply {
            putBoolean("dark_theme", darkTheme)
            commit()
        }

        // refresh the activity so new theme gets applied
        val intent = intent
        finish()
        startActivity(intent)

    }
}