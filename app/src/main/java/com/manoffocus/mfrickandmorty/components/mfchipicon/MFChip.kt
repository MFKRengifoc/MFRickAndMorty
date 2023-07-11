package com.manoffocus.mfrickandmorty.components.mfchipicon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

enum class MFChipSize(var size: Dp){
    LARGE(size = 60.dp),
    MEDIUM(size = 50.dp),
    SMALL(size = 40.dp),
    XSMALL(size = 30.dp);
    companion object {
        fun fromChipSize(mfChipSize: MFChipSize): MFTexSizes{
            return when(mfChipSize){
                LARGE -> {MFTexSizes.LARGE}
                MEDIUM -> {MFTexSizes.MEDIUM}
                SMALL -> {MFTexSizes.SMALL}
                XSMALL -> {MFTexSizes.XSMALL}
            }
        }
    }
}
@Composable
fun MFChip(
    size: MFChipSize = MFChipSize.MEDIUM,
    label: String,
    color: Color = MaterialTheme.colors.primary,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
            .clickable {
                onClick.invoke(label)
            },
        backgroundColor = color.copy(alpha = 0.1F, green = 0.8F),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(20.dp))
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
            .padding(vertical = verticalPaddingBg, horizontal = horizontalPaddingBg),
            contentAlignment = Alignment.Center
        ){
            MFText(
                modifier = Modifier
                    .clip(RoundedCornerShape(corner = CornerSize(20.dp))),
                text = label,
                color = color,
                size = MFChipSize.fromChipSize(size)
            )
        }
    }
}