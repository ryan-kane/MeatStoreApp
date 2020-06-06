package com.example.meatstoreapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity: AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener
{

    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mainPagerAdapter: MainContentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        viewPager = findViewById(R.id.main_content_view_pager)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        mainPagerAdapter = MainContentPagerAdapter(supportFragmentManager)

        mainPagerAdapter.setItems(arrayListOf(MainContent.HOME, MainContent.BUILD_ORDER, MainContent.PREVIOUS_ORDERS))

        val defaultScreen = MainContent.BUILD_ORDER
        showContent(defaultScreen)
        selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener(this)

        viewPager.adapter = mainPagerAdapter
        viewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val selectedContent = mainPagerAdapter.getItems()[position]
                selectBottomNavigationViewMenuItem(selectedContent.menuItemId)
            }
        })

    }

    private fun showContent(mainContent: MainContent) {
        val contentPostition = mainPagerAdapter.getItems().indexOf(mainContent)
        if(contentPostition != viewPager.currentItem) {
            // apparently the view pager will not move to the correct default page when called from
            // onCreate unless it is postDelayed
            // TODO:
            //  Decide if this is correct there are different ways to handle this
            viewPager.postDelayed(Runnable {
                viewPager.currentItem = contentPostition
            },100)

        }
    }

    private fun selectBottomNavigationViewMenuItem(@IdRes menuItemId: Int) {
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.selectedItemId = menuItemId
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MenuActivity", "Selected Item : $item")
        getMainContentForMenuItem(item.itemId)?.let {
            showContent(it)
            return true
        }
        return false
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        // reload the activity
        Log.d("MenuActivity", "Reselected Item : $item")
    }


}