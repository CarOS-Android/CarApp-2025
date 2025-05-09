package com.thoughtworks.carapp.service

import android.car.Car
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.Subscription
import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarService @Inject constructor(
    @ApplicationContext private val context: Context // 使用Hilt提供的上下文
) {
    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null
    private var propertyCallbacks: MutableList<PropertyCallback> = mutableListOf()

    fun connect() {
        car = Car.createCar(context)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as? CarPropertyManager
    }

    fun disconnect() {
        car?.disconnect()
        car = null
        carPropertyManager = null
    }

    fun registerPropertyListeners(callbacks: List<PropertyCallback>) {
        val subscriptions: List<Subscription> = callbacks.map { callback ->
            Subscription.Builder(callback.propertyId)
                .also { builder ->
                    if (callback.areaIds != null) {
                        callback.areaIds.forEach { id -> builder.addAreaId(id) }
                    }
                }.build()
        }

        carPropertyManager?.subscribePropertyEvents(subscriptions, null, commonCallback)
        propertyCallbacks.addAll(callbacks)
    }

    fun unregisterPropertyListeners(callbacks: List<PropertyCallback>) {
        callbacks.forEach { callback ->
            propertyCallbacks.remove(callback)
        }

        if (propertyCallbacks.size == 0) {
            carPropertyManager?.unsubscribePropertyEvents(commonCallback)
        }
    }

    private val commonCallback = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<Any?>) {
            propertyCallbacks
                .filter { callback ->
                    callback.propertyId == value.propertyId
                }
                .forEach { callback ->
                    callback.onChange.invoke(value.value, value.areaId)
                }
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.e("CarService", "Error listening to $propId")
        }
    }

    /**
     * 属性回调
     * @property propertyId 属性ID
     * @property areaIds 属性区域
     * @property onChange 属性被改变回调, Any是属性ID，Int是区域ID
     */
    class PropertyCallback(
        val propertyId: Int,
        val areaIds: List<Int>?,
        val onChange: (Any, Int) -> Unit
    )

    /**
     * 设置车辆属性的通用方法
     * @param clazz 属性值类型
     * @param propertyId 属性ID
     * @param value 属性值
     * @param areaId 区域ID
     */
    fun <E> setProperty(clazz: Class<E>, propertyId: Int, areaId: Int, value: E & Any) {
        try {
            carPropertyManager?.setProperty(clazz, propertyId, areaId, value)
        } catch (e: Exception) {
            Log.e("CarService", "Error setting property $propertyId: ${e.message}")
        }
    }

    /**
     * 设置车辆属性的通用方法
     * @param propertyId 属性ID
     * @param value 属性值
     * @param areaId 区域ID
     */
    fun <E> setProperty(propertyId: Int, areaId: Int, value: E & Any) {
        try {
            carPropertyManager?.setProperty(value.javaClass, propertyId, areaId, value)
        } catch (e: Exception) {
            Log.e("CarService", "Error setting property $propertyId: ${e.message}")
        }
    }

    fun <Value : Any> setPropertyForMultipleAreas(
        propertyId: Int,
        areaIds: List<Int>,
        value: Value
    ) {
        try {
            areaIds.forEach { id ->
                carPropertyManager?.setProperty(value.javaClass, propertyId, id, value)
            }
        } catch (e: Exception) {
            Log.e("CarService", "Error setting property $propertyId: ${e.message}")
        }
    }
}