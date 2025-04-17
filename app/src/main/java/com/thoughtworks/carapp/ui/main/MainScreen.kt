package com.thoughtworks.carapp.ui.main

import androidx.compose.runtime.Composable

@Composable
fun MainScreen() {
    Engine()
}

enum class Toogle {
    On,
    Off;

    fun toogle(): Toogle {
        return when(this){
            On -> Off
            Off -> On
        }
    }
}