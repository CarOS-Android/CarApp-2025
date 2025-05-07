package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun FanStateIcon(fanState: Int) {
    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Image(
            modifier = Modifier
                .size(106.dp)
                .padding(top = 20.dp),
            contentDescription = "风速",
            painter = when (fanState) {
                0 -> painterResource(id = R.drawable.ic_fan_speed_0)
                1 -> painterResource(id = R.drawable.ic_fan_speed_1)
                2 -> painterResource(id = R.drawable.ic_fan_speed_2)
                3 -> painterResource(id = R.drawable.ic_fan_speed_3)
                else -> painterResource(id = R.drawable.ic_fan_speed_4)
            },
        )
    }
}
