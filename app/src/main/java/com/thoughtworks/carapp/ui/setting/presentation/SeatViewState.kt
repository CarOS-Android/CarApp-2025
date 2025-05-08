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

data class FragranceSeatState(
    var driverFragrance: Int = 0,
    val copilotFragrance: Int = 0,
    val rearRawFragrance: Int = 0
)

enum class FragranceArea(val displayName: String, val areaId: Int) {
    DRIVER("驾驶位", 1),
    COPILOT("副驾驶位", 4),
    REAR("后排", 32);

    companion object {
        fun fromId(id: Int): FragranceArea? = entries.find { it.areaId == id }
    }
}
