package com.thoughtworks.carapp

import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.thoughtworks.carapp.ui.main.MainScreen
import com.thoughtworks.carapp.ui.setting.SettingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        setContent {
            var currentDestination by remember { mutableStateOf(Destination.Main) }
            Row {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF0E0E0F))
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.padding(vertical = 26.dp),
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = ""
                    )
                    SideNavigationBar(currentDestination) { currentDestination = it }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    when (currentDestination) {
                        Destination.Main -> MainScreen()
                        Destination.CarSetting -> SettingScreen()
                        else -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Screen", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SideNavigationBar(
    currentDestination: Destination,
    onSelected: (index: Destination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Destination.entries.forEach { destination ->
            Icon(
                modifier = Modifier
                    .selectable(
                        selected = destination == currentDestination,
                        onClick = {
                            onSelected.invoke(destination)
                        },
                        enabled = true,
                        role = Role.Image
                    )
                    .padding(horizontal = 42.dp),
                painter = painterResource(id = destination.icon),
                contentDescription = "",
                tint = if (currentDestination == destination) Color.White else Color.Unspecified
            )
        }
    }
}


enum class Destination(
    @DrawableRes val icon: Int
) {
    CarSetting(R.drawable.ic_car_setting),
    Navigation(R.drawable.ic_navigation),
    Main(R.drawable.ic_main),
    Music(R.drawable.ic_music),
    Setting(R.drawable.ic_setting)
}