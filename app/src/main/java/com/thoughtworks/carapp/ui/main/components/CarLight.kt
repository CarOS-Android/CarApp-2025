package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.presentation.CarLightState

@Composable
fun CarLight(
    carLightState: CarLightState,
    toggleHazardLights: () -> Unit,
    toggleHeadLights: () -> Unit,
    toggleHighBeamLights: () -> Unit
) {
    Image(
        painter = when (carLightState.hazardLightsState) {
            Toggle.Off -> painterResource(id = R.drawable.light1_off)
            Toggle.On -> painterResource(id = R.drawable.light1_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable {
                toggleHazardLights()
            },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (carLightState.headLightsState) {
            Toggle.Off -> painterResource(id = R.drawable.light2_off)
            Toggle.On -> painterResource(id = R.drawable.light2_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { toggleHeadLights() },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (carLightState.highBeamState) {
            Toggle.Off -> painterResource(id = R.drawable.light3_off)
            Toggle.On -> painterResource(id = R.drawable.light3_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { toggleHighBeamLights() },
        contentDescription = null
    )
}