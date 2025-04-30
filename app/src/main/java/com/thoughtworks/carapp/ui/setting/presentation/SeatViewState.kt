package com.thoughtworks.carapp.ui.setting.presentation

import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo

data class SingleSeatState(
    val heatingLevel: Int = 0,
    val coolingLevel: Int = 0,
    val messageLevel: Int = 0
) {
    fun getLevelFor(type: SeatOperationInfo): Int {
        return when (type) {
            SeatOperationInfo.HEATING -> heatingLevel
            SeatOperationInfo.COOLING -> coolingLevel
            SeatOperationInfo.MESSAGE -> messageLevel
        }
    }
}

data class SeatControlUiState(
    val driverSeat: SingleSeatState = SingleSeatState(),
    val copilotSeat: SingleSeatState = SingleSeatState()
) {
    fun getSeatState(areaSeat: AreaSeat): SingleSeatState {
        return when (areaSeat) {
            AreaSeat.DRIVER -> driverSeat
            AreaSeat.COPILOT -> copilotSeat
        }
    }
}
