package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun CarControl(viewModel: CarViewModel) {
    val engineState by viewModel.engineState.collectAsState()
    val autoHoldState by viewModel.autoHoldState.collectAsState()
    val parkingBrakeState by viewModel.parkingBrakeOnState.collectAsState()

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
            Engine(engineState)
        }

        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CenterCar(engineState, viewModel)
        }

        Row(
            modifier = Modifier
                .weight(0.3f)
                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(7f)
            ) {
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.offset(y = (-60).dp)) {
                    ParkingBrake(parkingBrakeState)
                }

                Row(modifier = Modifier.offset(y = 60.dp)) {
                    AutoHoldComponent(autoHoldState)
                }
            }
        }
    }
}

@Composable
fun CenterCar(state: Toggle, viewModel: CarViewModel) {
    if (state == Toggle.On) {
        CenterEnginOn(viewModel)
    } else {
        Image(
            painter = painterResource(id = R.drawable.car2),
            modifier = Modifier.width(400.dp),
            contentDescription = null
        )
    }

}

@Composable
fun CenterEnginOn(viewModel: CarViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box {
            Image(
                painter = painterResource(id = R.drawable.car1),
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .offset(y = 70.dp)
                    .align(Alignment.BottomCenter)
            )
            {
                CarLight(viewModel)
            }
        }
    }
}

