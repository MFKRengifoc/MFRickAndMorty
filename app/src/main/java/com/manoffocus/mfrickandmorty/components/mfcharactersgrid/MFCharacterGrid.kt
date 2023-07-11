package com.manoffocus.mfrickandmorty.components.mfcharactersgrid

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.characters.MFCharacter

@Composable
fun MFCharacterGrid(
    modifier: Modifier,
    listOfCharacters: List<MFCharacter>,
    columns: Int,
    onChooseAvatar: (Int, String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ){
        items(listOfCharacters){ character ->
            Log.d("MFCharacterGrid", "${character.id}")
            MFCharacterAvatar(
                modifier = Modifier,
                size = MFCharacterAvatarSize.SMALL,
                characterUrl = character.image,
                characterName = character.name,
            ){
                onChooseAvatar.invoke(character.id, character.image)
            }
        }
    }
}

enum class MFCharacterAvatarSize(var size: Dp){
    LARGE(size = 200.dp),
    MEDIUM(size = 100.dp),
    SMALL(size = 70.dp),
    XSMALL(size = 50.dp);
    companion object {
        fun fromCharacterSize(size: MFCharacterAvatarSize): MFTexSizes {
            return when(size){
                LARGE -> MFTexSizes.LARGE
                MEDIUM -> MFTexSizes.MEDIUM
                SMALL -> MFTexSizes.SMALL
                XSMALL -> MFTexSizes.XSMALL
            }
        }
    }
}

@Composable
fun MFCharacterAvatar(
    modifier: Modifier = Modifier,
    size: MFCharacterAvatarSize = MFCharacterAvatarSize.MEDIUM,
    characterUrl: String,
    characterName: String? = null,
    onChoose: () -> Unit
){
    Column(
        modifier = modifier.width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(
            space = 2.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .size(size.size)
                .padding(5.dp)
                .clip(CircleShape)
                .border(
                    1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colors.onPrimary
                )
                .clickable {
                    onChoose.invoke()
                },
            shape = CircleShape,
            elevation = 5.dp
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(characterUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mf_loading_avatar_icon),
                contentDescription = stringResource(R.string.mf_cd_placeholder_character) + characterName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape),
                error = painterResource(id = R.drawable.mf_alert_icon)
            )
        }
        characterName?.let { name ->
            MFText(
                text = name,
                size = MFCharacterAvatarSize.fromCharacterSize(size),
                align = TextAlign.Center
            )
        }
    }
}