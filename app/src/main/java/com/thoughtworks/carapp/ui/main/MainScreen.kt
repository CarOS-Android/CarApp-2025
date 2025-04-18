package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.thoughtworks.carapp.R

@Composable
fun MainScreen() {
    var autoHold by remember { mutableStateOf(Toggle.Off) }

    IconButton(
        onClick = {
            autoHold = autoHold.toggle()
        },
        modifier = Modifier.size(Dp(200F))
    ) {
        Image(
            painter = when (autoHold) {
                Toggle.Off -> painterResource(R.drawable.auto_hold_off)
                Toggle.On -> painterResource(R.drawable.auto_hold_on)
            },
            contentDescription = ""
        )
    }
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