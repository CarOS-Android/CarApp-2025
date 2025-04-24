package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun CarLight(viewModel: CarViewModel) {
    val highBeamState by viewModel.highBeamState.collectAsState()
    val hazardLightState by viewModel.hazardLightsState.collectAsState()
    val headLightsState by viewModel.headLightsState.collectAsState()
    Image(
        painter = when (hazardLightState) {
            Toggle.Off -> painterResource(id = R.drawable.light1_off)
            Toggle.On -> painterResource(id = R.drawable.light1_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable {
                viewModel.toggleHazardLights()
                       },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (headLightsState) {
            Toggle.Off -> painterResource(id = R.drawable.light2_off)
            Toggle.On -> painterResource(id = R.drawable.light2_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { viewModel.toggleHeadLights() },
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = when (highBeamState) {
            Toggle.Off -> painterResource(id = R.drawable.light3_off)
            Toggle.On -> painterResource(id = R.drawable.light3_on)
        },
        modifier = Modifier
            .width(85.dp)
            .clickable { viewModel.toggleHighBeamLights() },
        contentDescription = null
    )
}