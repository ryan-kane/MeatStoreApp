package com.example.meatstoreapp

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.meatstoreapp.BuildOrder.ProductsStore

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ProductStoreTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.meatstoreapp", appContext.packageName)
    }

    fun getAppContext(): Context? {
        return InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getProductsTest() {
        val appContext = getAppContext()
        val productStore =
            ProductsStore(appContext!!)
        productStore.getProducts()
    }

}
