package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toogle

@Composable
fun CarControl() {
    var state by remember { mutableStateOf(Toogle.Off) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Engine(
                state,
                onStateChange = { state = state.toogle() }
            )
        }

        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CenterCar(state)
        }

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxSize()
        ) {
            Engine(
                state,
                onStateChange = { state = state.toogle() }
            )
        }
    }
}

@Composable
fun CenterCar(state: Toogle) {
    if (state == Toogle.On) {
        CenterEnginOn(state)
    } else {
        Image(
            painter = painterResource(id = R.drawable.car2),
            contentDescription = null
        )
    }

}

@Composable
fun CenterEnginOn(state: Toogle) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.car1),
            contentDescription = null
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Image(
                painter = painterResource(id = R.drawable.light1_off),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(32.dp))
            Image(
                painter = painterResource(id = R.drawable.light2_off),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(32.dp))
            Image(
                painter = painterResource(id = R.drawable.light3_off),
                contentDescription = null
            )
        }
    }
}

