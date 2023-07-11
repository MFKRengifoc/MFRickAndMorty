package com.manoffocus.mfrickandmorty.components.mfcharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFCharacterFound(
    character: MFCharacter,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(corner = CornerSize(10.dp))
    Card(
        modifier = Modifier
            .clip(shape = shape)
            .padding(vertical = verticalPaddingBg * 2),
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick.invoke()
                }
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingBg),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val columnMod = Modifier
                .fillMaxHeight()
            Column(
                modifier = columnMod
                    .fillMaxWidth(0.3F),
                horizontalAlignment = Alignment.Start
            ) {
                MFCharacterFoundAvatar(character = character)
            }
            Column(
                modifier = columnMod
                    .fillMaxWidth(0.9F)
                    .padding(start = sidesPaddingBg),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                MFCharacterFoundInfo(character = character)
            }
            Column(
                modifier = columnMod
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .clip(shape)
                        .clickable {
                            onClick.invoke()
                        },
                    elevation = 0.dp,
                    backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.1F),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                    ){
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.mf_right_arrow_icon),
                            contentDescription = character.name,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MFCharacterFoundAvatar(
    character: MFCharacter
){
    Card(
        modifier = Modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Box(
            modifier = Modifier
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mf_placeholder_icon),
                contentDescription = stringResource(R.string.mf_cd_placeholder_character) + character.name,
                contentScale = ContentScale.FillHeight,
                error = painterResource(id = R.drawable.mf_alert_icon),
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = topBottomPaddingBg)
                        .height(20.dp)
                        .border(10.dp, Color.Transparent)
                        .background(
                            color = Color.Black.copy(0.8F),
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                bottomStart = 10.dp
                            )
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 5.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = getStatusColor(character.status),
                                shape = CircleShape
                            ),
                    )
                    MFText(
                        text = character.status,
                        size = MFTexSizes.SMALL
                    )
                }
            }
        }
    }
}

@Composable
fun MFCharacterFoundInfo(
    character: MFCharacter,
){
    val rowModifier = Modifier
    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .fillMaxHeight(0.5F),
        verticalAlignment = Alignment.Top
    ) {
        MFTextTitle(
            text = character.name,
            size = MFTexTitleSizes.SMALL,
            color = MaterialTheme.colors.secondary
        )
    }
    Row(
        modifier = rowModifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
            ) {
                MFTextTitle(
                    text = stringResource(id = R.string.mf_character_found_species_label),
                    size = MFTexTitleSizes.SMALL
                )
                MFText(
                    text = character.species,
                    size = MFTexSizes.SMALL
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start
                ) {
                    MFTextTitle(
                        text = stringResource(id = R.string.mf_character_found_origin_label),
                        size = MFTexTitleSizes.SMALL
                    )
                    MFText(
                        text = character.origin.name,
                        size = MFTexSizes.SMALL
                    )
                }
            }
        }
    }
}

private fun getStatusColor(type: String): Color{
    return when(type){
        "Alive" -> Color.Green
        "Dead" -> Color.Red
        else -> Color.White
    }
}