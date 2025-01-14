package com.manoffocus.mfrickandmorty.components.mflocations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButtonSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFHorizontal
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.ui.theme.mfButtonHPadding
import kotlin.random.Random



enum class MFLocationType(var type: String) {
    PLANET("planet"),
    CLUSTER("cluster"),
    MICROVERSE("microverse"),
    FANTASY_LOCATION("fantasy town"),
    DREAM("dream"),
    SPACE_STATION("space station");
    companion object {
        fun fromString(type: String) = when (type) {
            MFLocationType.PLANET.type -> PLANET
            MFLocationType.CLUSTER.type -> CLUSTER
            MFLocationType.MICROVERSE.type -> MICROVERSE
            MFLocationType.FANTASY_LOCATION.type -> FANTASY_LOCATION
            MFLocationType.DREAM.type -> DREAM
            MFLocationType.SPACE_STATION.type -> SPACE_STATION
            else -> PLANET
        }
    }
}

@Composable
fun MFLocations(
    modifier: Modifier,
    locationsList : List<MFLocation>,
    onLocationChosen: (MFLocation) -> Unit
){
    MFHorizontal(modifier = modifier, list = locationsList) {
        MFLocation(modifier = modifier, location = (it as MFLocation)){
            onLocationChosen.invoke(it)
        }
    }
}

@Composable
fun MFLocation(
    modifier: Modifier,
    location: MFLocation,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .padding(horizontal = mfButtonHPadding)
            .clickable { onClick.invoke() }
    ) {
        val imageResId = remember {
            getImageResIdByType(location.type)
        }
        MFLocationImage(
            modifier = Modifier
                .clickable { onClick.invoke() },
            imageResId = imageResId,
            name = location.name
        )
    }
}


private fun getRandomImageResId(): Int {
    val images = listOf(
        R.drawable.mf_planet_location_1,
        R.drawable.mf_planet_location_2
    )
    return images[Random.nextInt(images.size)]
}

private fun getImageResIdByType(locationType: String): Int {
    return when(MFLocationType.fromString(locationType.lowercase())){
        MFLocationType.CLUSTER -> R.drawable.mf_cluster_location
        MFLocationType.SPACE_STATION -> R.drawable.mf_space_station_location
        MFLocationType.MICROVERSE -> R.drawable.mf_planet_location_3
        MFLocationType.FANTASY_LOCATION -> R.drawable.mf_fantasy_location
        MFLocationType.DREAM -> R.drawable.mf_dream_location
        else -> getRandomImageResId()
    }
}

@Composable
fun MFLocationImage(modifier: Modifier, imageResId: Int, name: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Imagen",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            MFText(
                text = if (name.length > 15) name.take(15) + "..." else name,
                size = MFButtonSize.fromButtonSize(MFButtonSize.MEDIUM)
            )
        }
    }
}