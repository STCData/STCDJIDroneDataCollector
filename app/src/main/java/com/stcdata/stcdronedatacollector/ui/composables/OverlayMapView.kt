package com.stcdata.stcdronedatacollector

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.view.MapView
import com.stcdata.stcdronedatacollector.ui.tools.findActivity


@Composable
fun OverlayMapView(
    modifier: Modifier = Modifier,
    location: Location,
    ) {

    val viewpointScale = 72000.0
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates view
            MapView(context).apply {
                val activity = context.findActivity() as MainActivity
                val lifecycle = activity?.theLifecycle
                lifecycle?.addObserver(this)
                map = ArcGISMap(BasemapStyle.ArcGISTopographic)
                setViewpoint(Viewpoint(50.481254, 30.488687, viewpointScale))


            }
        },
        update = { view ->
            view.setViewpoint(Viewpoint(location.latitude, location.longitude, viewpointScale))
        }
    )
}