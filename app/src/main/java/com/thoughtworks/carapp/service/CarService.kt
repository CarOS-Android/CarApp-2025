package com.thoughtworks.carapp.service

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.util.Log

class CarService(private val context: Context) {
    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null

    fun connect() {
        car = Car.createCar(context)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as? CarPropertyManager
    }

    fun disconnect() {
        car?.disconnect()
        car = null
        carPropertyManager = null
    }

    fun getIgnitionState(): Int? {
        return try {
            carPropertyManager?.getProperty(
                VehiclePropertyIds.IGNITION_STATE,
                0
            )?.value as Int
        } catch (e: Exception) {
            Log.e("CarService", "Error getting ignition state", e)
            null
        }
    }
} 