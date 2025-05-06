package com.thoughtworks.carapp.ui.main.presentation

import com.thoughtworks.carapp.ui.main.components.TemperatureType

sealed interface ViewAction {
    object ToggleCarLock : ViewAction
    object ToggleHeadLights : ViewAction
    object ToggleHazardLights : ViewAction
    object ToggleHighBeamLights : ViewAction
    object ToggleFrontWindowDefog: ViewAction
    object ToggleRearWindowDefog: ViewAction
    object ToggleMirrorHeat: ViewAction
    object ToggleInternalCirculation: ViewAction
    data class OnSweepStep(val temperature: Float, val temperatureType: TemperatureType) : ViewAction
}
