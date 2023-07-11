package com.manoffocus.mfrickandmorty.components.mfchipicon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText

@Composable
fun MFChipInfoIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    value: String = "",
    fontSize: MFTexSizes = MFTexSizes.SMALL,
    horizontal: Boolean = false
){
    if (horizontal){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            MFIcon(
                modifier = Modifier,
                image = icon,
                contentDescription = value,
                size = MFIconSize.SMALL
            ){}
            MFText(
                text = value,
                size = fontSize
            )
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            MFIcon(
                modifier = Modifier,
                image = icon,
                contentDescription = value,
                size = MFIconSize.SMALL
            ){}
            MFText(
                text = value,
                size = fontSize
            )
        }
    }
}