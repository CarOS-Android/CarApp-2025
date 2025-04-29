package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.carapp.ui.main.components.CarControl
import com.thoughtworks.carapp.ui.main.components.CarMedia
import com.thoughtworks.carapp.ui.main.presentation.ViewAction
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun MainScreen(
    viewModel: CarViewModel = viewModel<CarViewModel>()
) {
    val state = viewModel.carState.collectAsState()
    val currentState = state.value

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
                carControlState = currentState.carControlState,
                carLightState = currentState.carLightState,
                carLockState = currentState.carLockState,
                acBoxState = currentState.acBoxState,
                toggleCarLock = {
                    viewModel.dispatch(ViewAction.ToggleCarLock)
                },
                onSweepStep = { it, temperatureType ->
                    viewModel.dispatch(ViewAction.OnSweepStep(it, temperatureType))
                },
                toggleHeadLights = {
                    viewModel.dispatch(ViewAction.ToggleHeadLights)
                },
                toggleHazardLights = {
                    viewModel.dispatch(ViewAction.ToggleHazardLights)
                },
                toggleHighBeamLights = {
                    viewModel.dispatch(ViewAction.ToggleHighBeamLights)
                }
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

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
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
