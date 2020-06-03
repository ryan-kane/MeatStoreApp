package com.example.meatstoreapp.BuildOrder

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.meatstoreapp.R

import kotlinx.android.synthetic.main.activity_build_order.*

class BuildOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_order)
        setSupportActionBar(toolbar)

    }

}
