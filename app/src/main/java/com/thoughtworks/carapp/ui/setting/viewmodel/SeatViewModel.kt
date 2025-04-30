package com.thoughtworks.carapp.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import com.thoughtworks.carapp.ui.setting.components.AreaSeat
import com.thoughtworks.carapp.ui.setting.components.SeatOperationInfo
import com.thoughtworks.carapp.ui.setting.model.SeatRepository
import com.thoughtworks.carapp.ui.setting.presentation.SeatControlUiState
import com.thoughtworks.carapp.ui.setting.presentation.SeatEvent
import com.thoughtworks.carapp.ui.setting.presentation.SingleSeatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(private val seatRepository: SeatRepository) : ViewModel() {
    private val _operationState = MutableStateFlow(SeatControlUiState())
    val operationState: StateFlow<SeatControlUiState> = _operationState.asStateFlow()

    init {
        setupRepositoryListeners()
    }
    
    private fun setupRepositoryListeners() {
        seatRepository.registerListeners { seat, type, level ->
            toggleOperationLevel(seat, type, level)
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        seatRepository.unregisterListeners()
    }

    private fun updateSeatState(
        state: SingleSeatState,
        type: SeatOperationInfo,
        newLevel: Int
    ): SingleSeatState {
        return when (type) {
            SeatOperationInfo.HEATING -> state.copy(
                heatingLevel = newLevel,
            )

            SeatOperationInfo.COOLING -> state.copy(
                coolingLevel = newLevel,
            )

            SeatOperationInfo.MESSAGE -> state.copy(
                messageLevel = newLevel
            )
        }
    }

    fun toggleOperationLevel(areaSeat: AreaSeat, type: SeatOperationInfo, level: Int?) {
        _operationState.update { currentState ->
            val currentSeatState = currentState.getSeatState(areaSeat)
            val newLevel = level ?: ((currentSeatState.getLevelFor(type) + 1) % 4)
            val updatedSeatState = updateSeatState(currentSeatState, type, newLevel)

            when (areaSeat) {
                AreaSeat.DRIVER -> currentState.copy(driverSeat = updatedSeatState)
                AreaSeat.COPILOT -> currentState.copy(copilotSeat = updatedSeatState)
            }
        }
    }

    fun handleEvent(event: SeatEvent) {
        when (event) {
            is SeatEvent.SingleSeatOperation -> {
                val seat = event.seat
                val type = event.operation
                val currentLevel = _operationState.value.getSeatState(seat).getLevelFor(type)
                val newLevel = (currentLevel + 1) % 4
                toggleOperationLevel(seat, type, newLevel)
                seatRepository.setSeatProperty(seat, type, newLevel)
            }
        }
    }
}
