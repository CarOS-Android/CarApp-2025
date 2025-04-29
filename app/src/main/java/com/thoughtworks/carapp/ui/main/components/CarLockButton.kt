package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.main.Lock

@Composable
fun CarLockButton(carLockState: Lock, toggleCarLock: () -> Unit) {

    Box(
        modifier = Modifier
            .size(88.dp, 43.dp)
            .clickable { toggleCarLock() }
    ) {
        Image(
            painter = when (carLockState) {
                Lock.Locked -> painterResource(R.drawable.car_lock)
                Lock.Unlocked -> painterResource(R.drawable.car_unlock)
            },
            contentDescription = null
        )
    }
}