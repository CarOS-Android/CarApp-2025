package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.carapp.ui.main.components.CarControl
import com.thoughtworks.carapp.ui.main.components.CarMedia
import com.thoughtworks.carapp.ui.main.components.TemperatureType
import com.thoughtworks.carapp.ui.main.presentation.CarState
import com.thoughtworks.carapp.ui.main.presentation.ViewAction

@Composable
fun MainScreen(
    state: CarState,
    toggleCarLock: () -> Unit,
    onSweepStep: (Float, TemperatureType) -> Unit,
    toggleHeadLights: () -> Unit,
    toggleHazardLights: () -> Unit,
    toggleHighBeamLights: () -> Unit,
    onAirVolumeChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
        ) {
            CarControl(
                carControlState = state.carControlState,
                carLightState = state.carLightState,
                carLockState = state.carLockState,
                acBoxState = state.acBoxState,
                toggleCarLock = toggleCarLock,
                onSweepStep = onSweepStep,
                onAirVolumeChange = onAirVolumeChange,
                toggleHeadLights = toggleHeadLights,
                toggleHazardLights = toggleHazardLights,
                toggleHighBeamLights = toggleHighBeamLights,
            )
        }
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
        ) {
            CarMedia()
        }
    }
}

@Preview(widthDp = 1408, heightDp = 792)
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = CarState(),
        toggleCarLock = {},
        onSweepStep = { _, _ -> },
        toggleHeadLights = {},
        toggleHazardLights = {},
        toggleHighBeamLights = {},
        onAirVolumeChange = { it ->
            viewModel.dispatch(ViewAction.OnAirVolumeChange(it))
        }
    )
}

enum class Toggle {
    On,
    Off;

    fun toggle(): Toggle {
        return when (this) {
            On -> Off
            Off -> On
        }
    }

    fun toBoolean(): Boolean {
        return when (this) {
            On -> true
            Off -> false
        }
    }
}

enum class Lock {
    Locked,
    Unlocked;

    fun switch(): Lock {
        return when (this) {
            Locked -> Unlocked
            Unlocked -> Locked
        }
    }

    infix fun or(other: Lock): Lock {
        // 如果任何一个是Unlocked，结果就是Unlocked
        return if (this == Unlocked || other == Unlocked) {
            Unlocked
        } else {
            Locked
        }
    }
}
