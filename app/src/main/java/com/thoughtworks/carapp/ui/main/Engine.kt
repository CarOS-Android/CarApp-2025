package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun Engine() {
    var state by remember { mutableStateOf(Toogle.Off) }

    Button(
        onClick = {
            state = state.toogle()
        },
        modifier = Modifier.background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Image(
            painter = when (state) {
                Toogle.Off -> painterResource(id = R.drawable.engine_off)
                Toogle.On -> painterResource(id = R.drawable.engine_on)
            },
            contentDescription = null
        )
    }
}