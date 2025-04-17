package com.thoughtworks.carapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.ui.main.components.CarControl
import com.thoughtworks.carapp.ui.main.components.CarMedium

@Composable
fun MainScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(0.6f)
                .background(Color.Blue)
        ) { CarControl() }

        Box(
            modifier = Modifier
                .weight(0.4f)
                .background(Color.Red)
        ) {
            CarMedium()
        }
    }
}

enum class Toogle {
    On,
    Off;

    fun toogle(): Toogle {
        return when (this) {
            On -> Off
            Off -> On
        }
    }
}