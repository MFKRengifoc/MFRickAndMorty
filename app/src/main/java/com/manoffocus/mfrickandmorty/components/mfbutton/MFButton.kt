package com.manoffocus.mfrickandmorty.components.mfbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg


enum class MFButtonSize(var w: Dp, var h: Dp){
    LARGE(w = 160.dp, h = 60.dp),
    MEDIUM(w = 150.dp, h = 50.dp),
    SMALL(w = 120.dp, h = 50.dp),
    XSMALL(w = 60.dp, h = 50.dp);
    companion object {
        fun fromButtonSize(size: MFButtonSize): MFTexSizes {
            return when(size){
                LARGE -> MFTexSizes.LARGE
                MEDIUM -> MFTexSizes.MEDIUM
                XSMALL -> MFTexSizes.XSMALL
                SMALL -> MFTexSizes.SMALL
            }
        }
    }
}

@Composable
fun MFButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    color: Color = MaterialTheme.colors.secondary,
    size: MFButtonSize = MFButtonSize.MEDIUM,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(size.h),
        onClick = {
            onClick.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
        ),
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 0.dp)
    ) {
        Box(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = color,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
            ) {

            }
            val changeCoef = 0.3F
            Row(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(
                        bottom = topBottomPaddingBg
                    )
                    .background(
                        color = color.copy(red = 0.1F, green = changeCoef, blue = 0.5F),
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                MFText(
                    modifier = Modifier
                        .padding(horizontal = horizontalPaddingBg),
                    text = text,
                    size = MFButtonSize.fromButtonSize(size)
                )
                icon?.let { ic ->
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = horizontalPaddingBg),
                        imageVector = ic,
                        contentDescription = text,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}