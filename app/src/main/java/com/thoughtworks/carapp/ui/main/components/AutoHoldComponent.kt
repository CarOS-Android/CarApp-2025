package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle

@Composable
fun AutoHoldComponent(autoHoldState: Toggle) {
    Image(
        painter = when (autoHoldState) {
            Toggle.Off -> painterResource(R.drawable.auto_hold_off)
            Toggle.On -> painterResource(R.drawable.auto_hold_on)
        },
        contentDescription = ""
    )
}

@Preview
@Composable
fun PreviewAutoHoldComponent() {
    AutoHoldComponent(Toggle.Off)
}