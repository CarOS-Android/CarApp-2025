package com.thoughtworks.carapp.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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
    val passengerSeat: SingleSeatState = SingleSeatState()
) {
    fun getSeatState(areaSeat: AreaSeat): SingleSeatState {
        return when (areaSeat) {
            AreaSeat.DRIVER -> driverSeat
            AreaSeat.PASSENGER -> passengerSeat
        }
    }
}

@HiltViewModel
class SeatViewModel @Inject constructor() : ViewModel() {
    private val _operationState = MutableStateFlow(SeatControlUiState())
    val operationState: StateFlow<SeatControlUiState> = _operationState.asStateFlow()

    fun updateSeatState(
        state: SingleSeatState,
        type: SeatOperationInfo,
        newLevel: Int
    ): SingleSeatState {
        return when (type) {
            SeatOperationInfo.HEATING -> state.copy(
                heatingLevel = newLevel,
                coolingLevel = 0
            )

            SeatOperationInfo.COOLING -> state.copy(
                coolingLevel = newLevel,
                heatingLevel = 0
            )

            SeatOperationInfo.MESSAGE -> state.copy(
                messageLevel = newLevel
            )
        }
    }

    fun toggleOperationLevel(areaSeat: AreaSeat, type: SeatOperationInfo) {
        _operationState.update { currentState ->
            val currentSeatState = currentState.getSeatState(areaSeat)
            val newLevel = (currentSeatState.getLevelFor(type) + 1) % 4
            val updatedSeatSeat = updateSeatState(currentSeatState, type, newLevel)

            when (areaSeat) {
                AreaSeat.DRIVER -> currentState.copy(driverSeat = updatedSeatSeat)
                AreaSeat.PASSENGER -> currentState.copy(passengerSeat = updatedSeatSeat)
            }
        }
    }
}
