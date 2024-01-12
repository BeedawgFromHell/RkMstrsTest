package com.example.featuire_cameras

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_domain.models.CameraModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun CamerasScreen(
    modifier: Modifier = Modifier,
    camerasMappedWithRooms: SnapshotStateMap<String, MutableList<CameraModel>> = remember { mutableStateMapOf() },
    onFavorite: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            camerasMappedWithRooms.forEach { roomWithCameras ->
                item {
                    Text(text = roomWithCameras.key)
                }

                items(roomWithCameras.value, key = { it.id }) { cameraModel ->
                    val dismissState = rememberDismissState(confirmValueChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onFavorite(cameraModel.id)
                        }
                        false
                    })
                    SwipeToDismiss(modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            color = Color.LightGray,
                                            shape = CircleShape
                                        )
                                        .padding(3.dp)
                                        .size(44.dp),
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }
                        },
                        dismissContent = {
                            CameraCard(cameraModel = cameraModel)
                        })
                }
            }
        })
}

@Composable
private fun CameraCard(modifier: Modifier = Modifier, cameraModel: CameraModel) {
    Column(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(16.dp), spotColor = Color.LightGray)
            .padding(1.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        Box() {
            AsyncImage(
                modifier = Modifier.aspectRatio(16f / 9f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cameraModel.snapshotUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            if (cameraModel.rec) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    text = "REC",
                    color = Color.Red
                )
            }

            if (cameraModel.favorites) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }

            IconButton(modifier = Modifier.align(Alignment.Center), onClick = {
                //TODO: PLAY
            }) {
                Icon(
                    modifier = Modifier.size(44.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Text(modifier = Modifier.padding(16.dp), text = cameraModel.name, fontSize = 18.sp)
    }

}


