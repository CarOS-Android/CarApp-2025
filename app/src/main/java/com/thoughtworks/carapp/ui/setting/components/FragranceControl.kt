package com.thoughtworks.carapp.ui.setting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.R
import com.thoughtworks.carapp.ui.setting.presentation.FragranceArea

@Composable
fun CyclableImageView(fragranceArea: FragranceArea) {
    val images = listOf(
        R.drawable.fragrance_button_off,
        R.drawable.fragrance_button_secret,
        R.drawable.fragrance_button_star,
        R.drawable.fragrance_button_sun
    )
    val area = fragranceArea
    var currentImageIndex by remember { mutableStateOf(0) }
    val currentImageResId = images[currentImageIndex]
    Image(
        painter = painterResource(id = currentImageResId),
        contentDescription = "点击切换属性",
        modifier = Modifier
            .size(100.dp, 100.dp)
            .clickable{
                currentImageIndex = (currentImageIndex + 1) % images.size
            }
    )
}




