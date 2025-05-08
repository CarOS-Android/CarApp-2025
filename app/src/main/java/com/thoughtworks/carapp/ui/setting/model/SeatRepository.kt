package com.thoughtworks.carapp.ui.setting.model

import android.car.VehicleAreaSeat
import android.car.VehiclePropertyIds
import com.thoughtworks.carapp.service.CarService
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo
import javax.inject.Inject

class SeatRepository @Inject constructor(private val carService: CarService) {
    private var propertyCallbacks: List<CarService.PropertyCallback> = listOf()
    
    // 注册监听器以获取座椅状态更新
    fun registerListeners(onStateUpdate: (AreaSeat, SeatOperationInfo, Int) -> Unit) {
        this.propertyCallbacks = listOf(
            // 座椅通风
            CarService.PropertyCallback(
                VehiclePropertyIds.HVAC_SEAT_VENTILATION,
                listOf(VehicleAreaSeat.SEAT_ROW_1_LEFT, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                val areaSeat = AreaSeat.getByAreaId(areaId)
                if (areaSeat != null) {
                    val level = getLevelFromCarValue(SeatOperationInfo.COOLING, value as Int)
                    onStateUpdate(areaSeat, SeatOperationInfo.COOLING, level)
                }
            },
            // 座椅加热
            CarService.PropertyCallback(
                VehiclePropertyIds.HVAC_SEAT_TEMPERATURE,
                listOf(VehicleAreaSeat.SEAT_ROW_1_LEFT, VehicleAreaSeat.SEAT_ROW_1_RIGHT)
            ) { value, areaId ->
                val areaSeat = AreaSeat.getByAreaId(areaId)
                if (areaSeat != null) {
                    val level = getLevelFromCarValue(SeatOperationInfo.HEATING, value as Int)
                    onStateUpdate(areaSeat, SeatOperationInfo.HEATING, level)
                }
            }
        )
        carService.registerPropertyListeners(this.propertyCallbacks)
    }
    
    // 取消监听器
    fun unregisterListeners() {
        carService.unregisterPropertyListeners(this.propertyCallbacks)
    }
    
    // 设置座椅属性值
    fun setSeatProperty(seat: AreaSeat, type: SeatOperationInfo, level: Int) {
        if (type.propertyId != null) {
            var adjustedLevel = level
            if (type.values != null && level != 0) {
                adjustedLevel = type.values[level - 1]
            }
            carService.setProperty(type.propertyId, seat.areaId, adjustedLevel)
        }
    }
    
    // 从车辆值转换为级别
    private fun getLevelFromCarValue(type: SeatOperationInfo, value: Int): Int {
        if (type.values != null && value != 0) {
            return type.values.indexOf(value) + 1
        }
        return when {
            value <= 3 -> value
            value <= 20 -> 1
            value <= 40 -> 2
            else -> 3
        }
    }
}