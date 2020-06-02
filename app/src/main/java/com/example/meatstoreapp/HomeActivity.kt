package com.example.meatstoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.meatstoreapp.BuildOrder.BuildOrderActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<Button>(R.id.button_build_order).setOnClickListener {
            // intent to build an order
            val buildOrderIntent = Intent(this, BuildOrderActivity::class.java)
            //  if information needs to be passed to the next activity, it can be put into the
            //  intent with putExtra(...)

            // launch the build order activity
            startActivity(buildOrderIntent)
        }
    }

}
