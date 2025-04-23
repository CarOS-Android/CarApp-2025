package com.thoughtworks.carapp

import android.app.Application
import com.thoughtworks.carapp.service.CarService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CarApplication : Application() {

    @Inject
    lateinit var carService: CarService // 触发注入

    override fun onCreate() {
        super.onCreate()
        // 访问 carService 确保实例化（可选，依赖注入已自动完成）
        carService.connect()
    }

    override fun onTerminate() {
        carService.disconnect()
        super.onTerminate()
    }
}