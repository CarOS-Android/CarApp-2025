package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle

@Composable
fun ParkingBrake(state: Toggle) {
    Image(
        painter = when (state) {
            Toggle.Off -> painterResource(id = R.drawable.hand_brake_off)
            Toggle.On -> painterResource(id = R.drawable.hand_brake_on)
        },
        contentDescription = null
    )
}