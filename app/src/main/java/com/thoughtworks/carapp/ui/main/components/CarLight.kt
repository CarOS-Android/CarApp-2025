package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun CarLight() {
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