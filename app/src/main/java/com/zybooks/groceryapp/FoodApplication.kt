package com.zybooks.groceryapp

import android.app.Application
import com.zybooks.groceryapp.data.FoodRepository

class FoodApplication: Application() {
    lateinit var foodRepository: FoodRepository

    // For onCreate() to run, android:name=".StudyHelperApplication" must
    // be added to <application> in AndroidManifest.xml
    override fun onCreate() {
        super.onCreate()
        foodRepository = FoodRepository(this.applicationContext)
    }
}