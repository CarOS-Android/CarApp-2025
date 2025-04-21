package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import com.thoughtworks.carapp.ui.main.Toggle

@Composable
fun CarControl() {
    var enginState by remember { mutableStateOf(Toggle.Off) }
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
            Engine(enginState)
        }

        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CenterCar(enginState)
        }

        Row(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier
                    .weight(7f)
            ) {
            }
            Row(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AutoholdComponent()
            }
        }
    }
}

@Composable
fun CenterCar(state: Toggle) {
    if (state == Toggle.On) {
        CenterEnginOn()
    } else {
        Image(
            painter = painterResource(id = R.drawable.car2),
            modifier = Modifier.width(400.dp),
            contentDescription = null
        )
    }

}

@Composable
fun CenterEnginOn() {
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
                CarLight()
            }
        }
    }
}

@Composable
private fun CarLight() {
    // todo 后续可以根据车灯的状态来展示具体的车灯图片
    Image(
        painter = painterResource(id = R.drawable.light1_off),
        modifier = Modifier.width(85.dp),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = painterResource(id = R.drawable.light2_off),
        modifier = Modifier.width(85.dp),
        contentDescription = null
    )
    Spacer(modifier = Modifier.width(32.dp))
    Image(
        painter = painterResource(id = R.drawable.light3_off),
        modifier = Modifier.width(85.dp),
        contentDescription = null
    )
}

