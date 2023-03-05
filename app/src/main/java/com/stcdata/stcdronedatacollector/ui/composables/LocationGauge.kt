package com.stcdata.stcdronedatacollector.ui.composables

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.stcdata.stcdronedatacollector.R

fun dummyLocation(longitude: Double, latitude: Double): Location {
    val location = Location("dummyprovider")
    location.longitude = longitude
    location.latitude = latitude
    return location
}

class LocationPreviewParamProvider : PreviewParameterProvider<Location?> {
    override val values: Sequence<Location?> = sequenceOf(
        null,
        dummyLocation(40.324994, 123.43200430),
        dummyLocation(4.4545, 13.4320030),
    )
}


@Preview
@Composable
fun LocationGauge(
    @PreviewParameter(LocationPreviewParamProvider::class)  location: Location?) {
    fun formatDouble(value: Double?): String {
        return value ?.let {
            val beforePoint = String.format("%3d", it.toInt()) //.replace(' ', '0')
            val afterPoint = String.format("%.2f", it).split(".")[1].takeLast(2)
            "$beforePoint.~$afterPoint"
        } ?: "000.~00"

    }
    val lat = formatDouble(location?.latitude)
    val long = formatDouble(location?.longitude)

    Row {

        Image(painter = painterResource(id = R.drawable.topbar_gps), contentDescription = "",  modifier = Modifier.align(Alignment.CenterVertically))
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(lat, style = TextStyle(fontFamily = FontFamily.Monospace))
            Text(long, style = TextStyle(fontFamily = FontFamily.Monospace))
        }
    }


}
