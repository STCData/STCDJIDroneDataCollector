package com.stcdata.stcdronedatacollector.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stcdata.stcdronedatacollector.*
import com.stcdata.stcdronedatacollector.R
import com.stcdata.stcdronedatacollector.ui.composables.LocationGauge
import org.like.a.fly.ui.composables.HorizontalProgressBarWithPercentLabel
import org.like.a.fly.ui.composables.ToolButton


@Composable
fun VisibilityAndEnability(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {

    Box(modifier = modifier
        .alpha(if (visible) 1f else 0.1f)
        .let {
            return@let if (!visible) {
                it.clickable {}
                it.size(0.dp)
            } else it
        },
    ) {

        content()
    }
}


@Composable
fun MainScreenContent(
    state: MainScreenUiState,
    onTBMapMode: () -> Unit = {},
) {

    Box {
        VisibilityAndEnability(visible = state.mapIsScene) {
                OverlaySceneView(
                    location = state.droneState.location(),
                    pitch = state.droneState.pitch,
                    roll = state.droneState.roll,
                    altitude = state.droneState.altitude,
                    heading = state.droneState.heading)
        }
        VisibilityAndEnability(visible = !state.mapIsScene) {
                OverlayMapView(location = state.droneState.location())
        }



        Row (Modifier.height(40.dp)){
            Spacer(modifier = Modifier.width(2.dp))
            LocationGauge(location = state.droneState.location())
            Spacer(modifier = Modifier.width(10.dp))
            Spacer(Modifier.weight(1f))

            HorizontalProgressBarWithPercentLabel(value = state.droneState.battery,modifier = Modifier.height(40.dp))

        }


        Row (modifier = Modifier.align(Alignment.BottomStart)) {
            Column(modifier = Modifier.align(Alignment.Bottom)) {
                ToolButton(
                    image = painterResource(R.drawable.tb_map_mode),
                    isChecked = state.mapIsScene,
                    onClick = onTBMapMode
                )
            }
        }


    }


}


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,

    ) {
        val viewState by viewModel.state.collectAsState()
        MainScreenContent(
            state = viewState,
            onTBMapMode = viewModel::onTBMapMode,
            )
    }


