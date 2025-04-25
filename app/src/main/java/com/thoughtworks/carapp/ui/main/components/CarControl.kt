package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Toggle
import com.thoughtworks.carapp.ui.main.viewmodel.CarViewModel

@Composable
fun CarControl(viewModel: CarViewModel) {
    val engineState by viewModel.engineState.collectAsState()
    val autoHoldState by viewModel.autoHoldState.collectAsState()
    val parkingBrakeState by viewModel.parkingBrakeOnState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Clock(modifier = Modifier.weight(1f))
            Engine(
                modifier = Modifier
                    .size(200.dp),
                state = engineState
            )
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
            Row(modifier = Modifier.weight(7f)) {
                AcBox()
            }
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize(), contentAlignment = Alignment.Center
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
        Box(
            modifier = Modifier
                .width(531.dp)
                .height(187.dp)
        ) {
            Image(
                modifier = Modifier.align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.car2),
                contentDescription = null
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = 27.dp),
            ) {
                CarLockButton(viewModel)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-15).dp, y = 16.dp)
            ) {
                HoodLockButton()
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 0.dp, y = (-30).dp),
            ) {
                TrunkLockButton()
            }
        }
    }
}

@Preview(
    widthDp = 625, heightDp = 220
)
@Composable
fun PreviewCenterCar() {
    CenterCar(
        Toggle.Off, viewModel = TODO()
    )
}

@Composable
fun CenterEnginOn(viewModel: CarViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.car1), contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .offset(y = 70.dp)
                    .align(Alignment.BottomCenter)
            ) {
                CarLight(viewModel)
            }
        }
    }
}


@Composable
private fun Clock(modifier: Modifier) {
    Row(
        modifier = modifier.padding(start = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.height(100.dp),
            text = "08:06",
            fontSize = 80.sp,
            color = Color(0xFFF1F3F5)
        )
        Image(
            modifier = Modifier.height(100.dp),
            painter = painterResource(R.drawable.ic_siri),
            contentDescription = null
        )
        Image(
            modifier = Modifier.height(100.dp),
            painter = painterResource(R.drawable.ic_volume),
            contentDescription = null
        )
    }
}

@Composable
private fun AcBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(start = 51.dp)
            .size(width = 663.dp, height = 228.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_temperature_bg),
            contentDescription = null
        )
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_temperature_bg_rec),
            contentDescription = null
        )

        Row(modifier = Modifier.padding(22.dp)) {
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_temp_driver),
                contentDescription = null
            )
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_fan),
                contentDescription = null
            )
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_temp_codriver),
                contentDescription = null
            )
        }
    }
}