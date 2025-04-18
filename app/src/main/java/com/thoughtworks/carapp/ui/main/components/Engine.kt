package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle

@Composable
fun Engine(state : Toggle, onStateChange: () -> Unit) {
    Button(
        onClick = {
            onStateChange()
        },
        modifier = Modifier.background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Image(
            painter = when (state) {
                Toggle.Off -> painterResource(id = R.drawable.engine_off)
                Toggle.On -> painterResource(id = R.drawable.engine_on)
            },
            contentDescription = null
        )
    }
}