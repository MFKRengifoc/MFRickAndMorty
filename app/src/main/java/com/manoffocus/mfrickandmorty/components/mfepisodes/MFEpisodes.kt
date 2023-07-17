package com.manoffocus.mfrickandmorty.components.mfepisodes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mfscrollviews.MFVertical
import com.manoffocus.mfrickandmorty.components.mfsection.MFSectionForVertical
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.models.episodes.MFEpisode
import com.manoffocus.mfrickandmorty.ui.theme.mfTextSidesPadding
import com.manoffocus.mfrickandmorty.ui.theme.mfTextTopBottomPadding
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFEpisodes(
    modifier: Modifier,
    list: List<MFEpisode>,
    content: @Composable() (MFEpisode) -> Unit
){
    MFVertical(
        modifier = modifier,
        list = list
    ) { episode ->
        content(episode as MFEpisode)
    }
}

@Composable
fun MFEpisodeOverView(
    modifier: Modifier = Modifier,
    episode: MFEpisode,
    contentCharacterRow: @Composable() () -> Unit
    ){
    Card(
        modifier = modifier
            .padding(vertical = verticalPaddingBg * 2)
            .fillMaxWidth()
            .height(220.dp),
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = modifier
        ) {
            MFSectionForVertical(
                modifier = modifier
                    .fillMaxHeight(0.4F)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    MFTextTitle(
                        modifier = Modifier
                            .padding(horizontal = mfTextSidesPadding),
                        text = episode.name,
                        size = MFTexTitleSizes.MEDIUM,
                        underLine = true,
                        maxWidth = 300.dp
                    )
                    MFTextTitle(
                        modifier = Modifier
                            .padding(horizontal = mfTextSidesPadding),
                        text = episode.air_date,
                        size = MFTexTitleSizes.SMALL,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
            MFSectionForVertical(
                modifier = modifier,
                horizontalAlignmentC = Alignment.Start
            ) {
                MFTextTitle(
                    modifier = Modifier
                        .padding(horizontal = mfTextSidesPadding)
                        .padding(bottom = mfTextTopBottomPadding),
                    text = stringResource(id = R.string.mf_season_screen_starring_label),
                    size = MFTexTitleSizes.MEDIUM
                )
                contentCharacterRow()
            }
        }
    }
}