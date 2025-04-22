package com.thoughtworks.carapp.service

import android.car.Car
import android.car.VehicleAreaType
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.util.Log

class CarService(private val context: Context) {
    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null
    private var ignitionStateChangeListener: ((Int) -> Unit)? = null

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
            carPropertyManager?.getProperty<Int>(
                VehiclePropertyIds.IGNITION_STATE,
                VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL
            )?.value as Int
        } catch (e: Exception) {
            Log.e("CarService", "Error getting ignition state", e)
            null
        }
    }

    fun registerIgnitionStateListener(listener: (Int) -> Unit) {
        ignitionStateChangeListener = listener
        carPropertyManager?.registerCallback(
            ignitionCallback,
            VehiclePropertyIds.IGNITION_STATE,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    fun unregisterIgnitionStateListener() {
        carPropertyManager?.unregisterCallback(ignitionCallback)
        ignitionStateChangeListener = null
    }

    private val ignitionCallback = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<Any?>) {
            if (value.propertyId == VehiclePropertyIds.IGNITION_STATE) {
                val state = value.value as Int
                ignitionStateChangeListener?.invoke(state)
            }
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.e("CarService", "Error listening to IGNITION_STATE")
        }
    }
}