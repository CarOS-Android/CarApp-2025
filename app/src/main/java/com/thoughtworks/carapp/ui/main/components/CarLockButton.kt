package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun CarLockButton() {
    Image(
        painter = painterResource(id = R.drawable.car_lock),
        contentDescription = null
    )
}