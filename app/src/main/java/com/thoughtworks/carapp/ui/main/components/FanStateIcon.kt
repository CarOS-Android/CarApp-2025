package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun FanStateIcon(fanState: Int) {
    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Image(
            modifier = Modifier,
            painter = when (fanState) {
                0 -> painterResource(id = R.drawable.ic_dock_media_ctrl_border)
                else -> painterResource(id = R.drawable.ic_fan)
            },
            contentDescription = null
        )
    }
}
