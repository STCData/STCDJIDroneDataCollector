package com.stcdata.stcdronedatacollector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.stcdata.stcdronedatacollector.models.Drone
import com.stcdata.stcdronedatacollector.ui.screens.MainScreen
import com.stcdata.stcdronedatacollector.ui.theme.STCDroneDataCollectorTheme

class MainActivity : ComponentActivity() {
    val theLifecycle: Lifecycle
        get() {
            return lifecycle
        }


    private inline fun viewModelFactory(crossinline f: () -> MainScreenViewModel) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
        }

    private val viewModel  : MainScreenViewModel by lazy {


        ViewModelProvider(
            this,
            viewModelFactory {
                val useDummyDrone = false
                val drone =  Drone()
                MainScreenViewModel(
                    application = application,
                    drone = drone) }
        )[MainScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArcGISEnvironment.apiKey = ApiKey.create("AAPK1a14253a7e6b476c9648312fc1d1b93ePs6mhIbwqHdcng4sIUSwyGJVOn4XPoxlu98b7f-QcRa7gOSe25gRIloKLD4oHMGq")

        setContent {
            STCDroneDataCollectorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen(viewModel)


                }
            }
        }
    }
}
