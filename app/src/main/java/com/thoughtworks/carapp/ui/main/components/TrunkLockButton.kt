package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.thoughtworks.carapp.R

@Composable
fun TrunkLockButton() {
    Image(
        painter = painterResource(id = R.drawable.trunk_unlock),
        contentDescription = null
    )
}