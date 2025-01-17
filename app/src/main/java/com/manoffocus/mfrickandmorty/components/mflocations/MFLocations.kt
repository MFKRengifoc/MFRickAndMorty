package com.manoffocus.mfrickandmorty.components.mflocations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButtonSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFHorizontal
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.db.Location
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.ui.theme.mfButtonHPadding
import kotlin.random.Random


enum class MFLocationSize(var size: Dp){
    XLARGE(size = 260.dp),
    LARGE(size = 200.dp),
    MEDIUM(size = 100.dp),
    SMALL(size = 70.dp),
    XSMALL(size = 50.dp);
    companion object {
        fun fromCharacterSize(size: MFLocationSize): MFTexSizes {
            return when(size){
                XLARGE -> MFTexSizes.XLARGE
                LARGE -> MFTexSizes.LARGE
                MEDIUM -> MFTexSizes.MEDIUM
                SMALL -> MFTexSizes.SMALL
                XSMALL -> MFTexSizes.XSMALL
            }
        }
    }
}


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
    horizontal: Boolean = true,
    state: LazyListState = rememberLazyListState(),
    onLoadMoreLocations: (() -> Unit)? = null,
    onLocationChosen: (MFLocation) -> Unit
){
    if (horizontal){
        MFHorizontal(modifier = modifier, list = locationsList) {
            MFLocation(modifier = modifier, location = (it as MFLocation)){
                onLocationChosen.invoke(it)
            }
        }
    } else {
        MFVertical(
            modifier = modifier,
            state = state,
            list = locationsList
        ) {
            MFLocationCardView(modifier = modifier, location = (it as MFLocation)){
                onLocationChosen.invoke(it)
            }
            var lastItem = locationsList.last() == it
            if (lastItem){
                MFButton(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.mf_all_locations_screen_more_button_label)
                ){
                    onLoadMoreLocations?.invoke()
                    lastItem = false
                }
            }
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

@Composable
fun MFVisitedLocation(
    modifier: Modifier,
    size: MFLocationSize = MFLocationSize.MEDIUM,
    location: Location,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .padding(horizontal = mfButtonHPadding)
            .clickable { onClick.invoke() }
    ) {
        MFLocationImage(
            modifier = Modifier
                .clickable { onClick.invoke() },
            size = size,
            imageResId = getImageResIdByType(location.type)
        )
    }
}

@Composable
fun MFLocationCardView(
    modifier: Modifier = Modifier,
    location: MFLocation,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Bottom)
            ) {
                val imageResId = remember { getImageResIdByType(location.type) }
                MFLocationImage(
                    modifier = Modifier.fillMaxSize(),
                    imageResId = imageResId,
                    name = location.name,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                MFText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = location.name,
                    size = MFButtonSize.fromButtonSize(MFButtonSize.MEDIUM)
                )
                MFText(
                    text = location.type,
                    size = MFButtonSize.fromButtonSize(MFButtonSize.SMALL)
                )
                MFText(
                    text = stringResource(id = R.string.mf_all_locations_screen_residents_number_label, location.residents.size),
                    size = MFButtonSize.fromButtonSize(MFButtonSize.SMALL)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.mf_right_arrow_icon),
                contentDescription = "Arrow",
                modifier = Modifier.size(16.dp)
            )
        }
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
fun MFLocationImage(
    modifier: Modifier,
    size: MFLocationSize = MFLocationSize.MEDIUM,
    imageResId: Int,
    name: String? = null
) {
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
                .size(size.size)
        )
        Spacer(modifier = Modifier.height(8.dp))
        name?.let {
            Box {
                MFText(
                    text = if (name.length > 15) name.take(15) + "..." else name,
                    size = MFButtonSize.fromButtonSize(MFButtonSize.MEDIUM)
                )
            }
        }
    }
}