package com.thoughtworks.carapp.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R

@Composable
fun CarMedia() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, end = 30.dp, bottom = 60.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_nav_map),
            contentDescription = ""
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_music),
            contentDescription = null
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_switch),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewCarMedia() {
    CarMedia()
}
