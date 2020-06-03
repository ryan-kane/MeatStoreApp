package com.example.meatstoreapp

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.meatstoreapp.BuildOrder.BuildOrderFragment

enum class MainContent(@IdRes val menuItemId: Int,
    // TODO : add icons for bottom navigation

//                       @DrawableRes val menuItemIcon: Int,
                       @StringRes val titleStringId: Int,
                       val fragment: Fragment) {
    HOME(R.id.bottom_navigation_item_home, R.string.bottom_navigation_item_home_title, HomeFragment()),
    BUILD_ORDER(R.id.bottom_navigation_item_build_order, R.string.bottom_navigation_item_build_order_title, BuildOrderFragment()),
    PREVIOUS_ORDERS(R.id.bottom_navigation_item_previous_orders, R.string.bottom_navigation_item_previous_orders_title, PreviousOrdersFragment())
}

fun getMainContentForMenuItem(menuItemId: Int): MainContent? {
    for (mainContent in MainContent.values()) {
        if (mainContent.menuItemId == menuItemId) {
            return mainContent
        }
    }
    return null
}