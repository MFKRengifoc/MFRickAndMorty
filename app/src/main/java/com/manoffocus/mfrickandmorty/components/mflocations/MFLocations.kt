package com.manoffocus.mfrickandmorty.components.mflocations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButton
import com.manoffocus.mfrickandmorty.components.mfbutton.MFButtonSize
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFHorizontal
import com.manoffocus.mfrickandmorty.models.locations.MFLocation
import com.manoffocus.mfrickandmorty.ui.theme.mfButtonHPadding

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
        MFButton(
            modifier = Modifier
                .clickable { onClick.invoke() },
            text = location.name,
            size = MFButtonSize.MEDIUM,
            color = MaterialTheme.colors.primary
        )
    }
}