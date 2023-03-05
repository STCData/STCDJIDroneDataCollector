package com.stcdata.stcdronedatacollector

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.*
import com.arcgismaps.mapping.view.Camera
import com.arcgismaps.mapping.view.SceneView
import com.stcdata.stcdronedatacollector.ui.tools.findActivity


@Composable
fun OverlaySceneView(
    modifier: Modifier = Modifier,
    location: Location,
    pitch: Double,
    roll: Double,
    altitude: Double,
    heading: Double
    ) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates view
            SceneView(context).apply {
                val activity = context.findActivity() as MainActivity
                val lifecycle = activity?.theLifecycle
                lifecycle?.addObserver(this)
                var s = ArcGISScene(BasemapStyle.ArcGISImageryStandard)
                val elevationSource = ArcGISTiledElevationSource("https://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer")
                val surface = Surface()
                surface.elevationSources.add(elevationSource)
                // add an exaggeration factor to increase the 3D effect of the elevation.
                surface.elevationExaggeration = 1f
                s.baseSurface = surface
                scene = s
            }
        },
        update = { view ->
            // Point(x, y, z, spatialReference)
            val cameraLocation = Point(location.latitude, location.longitude, altitude, SpatialReference.wgs84())
            // Camera(location, heading, pitch, roll)
            val camera = Camera(cameraLocation, heading, pitch, roll)
            view. setViewpoint(Viewpoint(location.latitude, location.longitude, 1.0, camera))
        }
    )
}