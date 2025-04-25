package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun TrunkLockButton() {
    Image(
        painter = painterResource(id = R.drawable.trunk_lock),
        modifier = Modifier
            .size(38.dp, 38.dp),
        contentDescription = null
    )
}