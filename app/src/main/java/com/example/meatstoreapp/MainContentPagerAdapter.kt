package com.example.meatstoreapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.text.FieldPosition

class MainContentPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private val screens = arrayListOf<MainContent>()

    fun setItems(screens: List<MainContent>) {
        this.screens.apply {
            clear()
            addAll(screens)
            notifyDataSetChanged()
        }
    }

    fun getItems(): List<MainContent> {
        return screens
    }

    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

    override fun getCount(): Int {
        return screens.size
    }
}