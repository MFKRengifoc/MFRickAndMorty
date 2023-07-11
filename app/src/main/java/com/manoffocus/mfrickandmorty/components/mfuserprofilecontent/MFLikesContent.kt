package com.manoffocus.mfrickandmorty.components.mfuserprofilecontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.components.mfcharactersgrid.MFCharacterAvatar
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.db.CharacterLike
import com.manoffocus.mfrickandmorty.ui.theme.mfUserProfileScreenFavouriteColor
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg

@Composable
fun MFLikesContent(
    modifier: Modifier,
    likes: List<CharacterLike>,
    onAvatarChosen: (Int) -> Unit,
    onClickFavourite: (CharacterLike) -> Unit
) {
    MFVertical(
        modifier = modifier
            .fillMaxWidth(),
        list = likes
    ) { l ->
        val like = l as CharacterLike
        Card(
            modifier = modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = sidesPaddingBg)
                .clip(RoundedCornerShape(corner = CornerSize(5.dp))),
            elevation = 0.dp,
            backgroundColor = Color.Transparent
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7F)
                        .fillMaxHeight(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.Start
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        MFCharacterAvatar(
                            characterUrl = like.characterImage,
                            characterName = like.name
                        ){
                            onAvatarChosen.invoke(like.characterId)
                        }
                        MFText(
                            text = like.name
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MFIcon(
                        modifier = Modifier,
                        contentDescription = like.name,
                        iconVector = Icons.Default.Favorite,
                        tint = mfUserProfileScreenFavouriteColor
                    ){
                        onClickFavourite.invoke(like)
                    }
                }
            }
        }
    }
}