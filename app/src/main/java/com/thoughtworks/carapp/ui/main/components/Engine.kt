package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle

@Composable
fun Engine(modifier: Modifier, state: Toggle) {
    Image(
        modifier = modifier,
        painter = when (state) {
            Toggle.Off -> painterResource(id = R.drawable.engine_off)
            Toggle.On -> painterResource(id = R.drawable.engine_on)
        },
        contentDescription = null
    )
}