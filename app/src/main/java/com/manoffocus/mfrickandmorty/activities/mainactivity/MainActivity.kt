package com.manoffocus.mfrickandmorty.activities.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.manoffocus.mfrickandmorty.navigation.MFNavigation
import com.manoffocus.mfrickandmorty.ui.theme.MFRickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MFRickAndMortyApp()
        }
    }
    companion object {
        const val TAG = "MainActivity"
    }
}

@Composable
fun MFRickAndMortyApp(
    viewModel: MainViewModel = hiltViewModel()
) {
    MFRickAndMortyTheme() {
        val networkStatus = viewModel.networkStatus
        val navController = rememberNavController()
        val ctx = LocalContext.current
        LaunchedEffect(Unit){
            viewModel.load()
            viewModel.getNetworkStatus(ctx)
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            color = MaterialTheme.colors.background
        ) {
            MFNavigation(
                viewModel = viewModel,
                networkStatus = networkStatus,
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MFRickAndMortyApp()
}