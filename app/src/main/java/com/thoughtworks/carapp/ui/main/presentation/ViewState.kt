package com.thoughtworks.carapp.ui.main.presentation

import com.thoughtworks.carapp.ui.main.Lock
import com.thoughtworks.carapp.ui.main.Toggle

/**
 * engineState: 车辆引擎状态
 * autoHoldState: 自动驻车
 * parkingBrakeState: 刹车状态
 * */
data class CarControlState(
    val engineState: Toggle = Toggle.Off,
    val autoHoldState: Toggle = Toggle.Off,
    val parkingBrakeState: Toggle = Toggle.On,
)

/**
 * highBeamState: 远光灯
 * hazardLightsState: 危险信号灯-示宽灯
 * headLightsState: 车前灯-近光灯
 * */
data class CarLightState(
    val highBeamState: Toggle = Toggle.Off,
    val hazardLightsState: Toggle = Toggle.Off,
    val headLightsState: Toggle = Toggle.Off,
)

/**
 * driverTemperature: 主驾-空调温度
 * coPilotTemperature: 副驾-空调温度
 * */
data class AcBoxState(
    val driverTemperature: Float = 0.0f,
    val coPilotTemperature: Float = 0.0f,
)

data class CarState(
    val carControlState: CarControlState = CarControlState(),
    val carLightState: CarLightState = CarLightState(),
    // 门锁状态
    val carLockState: Lock = Lock.Locked,
    val acBoxState: AcBoxState = AcBoxState()
)
