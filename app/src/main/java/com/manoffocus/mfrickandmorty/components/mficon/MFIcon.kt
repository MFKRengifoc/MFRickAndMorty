package com.manoffocus.mfrickandmorty.components.mficon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.ui.theme.mfIconSidePadding


enum class MFIconSize(var size: Dp){
    LARGE(size = 60.dp),
    MEDIUM(size = 50.dp),
    SMALL(size = 40.dp),
    XSMALL(size = 30.dp);
}

@Composable
fun MFIcon(
    modifier: Modifier,
    iconVector: ImageVector? = null,
    image: Int? = null,
    contentDescription: String,
    tint: Color = MaterialTheme.colors.onPrimary,
    size: MFIconSize = MFIconSize.MEDIUM,
    elevation: Dp = 0.dp,
    backgroundColor : Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clip(CircleShape),
        elevation = elevation,
        backgroundColor = backgroundColor,
        shape = CircleShape
    ) {
        iconVector?.let { icon ->
            Icon(
                modifier = Modifier
                    .clickable {
                        onClick.invoke()
                    }
                    .size(size.size)
                    .padding(all = mfIconSidePadding),
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
            )
        }?: run {
            image?.let { im ->
                Image(
                    modifier = Modifier
                        .clickable {
                            onClick.invoke()
                        }
                        .size(size.size)
                        .padding(all = mfIconSidePadding),
                    painter = painterResource(id = im),
                    contentDescription = contentDescription
                )
            } ?: run {
                Image(
                    modifier = Modifier
                        .clickable {
                            onClick.invoke()
                        }
                        .size(size.size)
                        .padding(all = mfIconSidePadding),
                    painter = painterResource(id = R.drawable.mf_placeholder_icon),
                    contentDescription = contentDescription
                )
            }
        }

    }
}