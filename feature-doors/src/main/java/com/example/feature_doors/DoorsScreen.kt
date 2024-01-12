package com.example.feature_doors

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_domain.models.DoorModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun DoorsScreen(
    modifier: Modifier = Modifier,
    doors: SnapshotStateList<DoorModel> = remember { mutableStateListOf() },
    onFavorite: (Int) -> Unit = {},
    onChangeName: (DoorModel, String) -> Unit = { _, _ -> },
) {
    val width = LocalConfiguration.current.screenWidthDp
    val changeNameDoorModel = remember { mutableStateOf<DoorModel?>(null) }

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(doors, key = { it.id }) { doorModel ->
                val swipeState = rememberSwipeableState(initialValue = 0)
                val anchors = mapOf(
                    0f to 0,
                    with(LocalDensity.current) { -(width.dp.toPx() * 0.1f) } to 1
                )

                Box(
                    modifier = Modifier
                        .swipeable(
                            swipeState,
                            anchors = anchors,
                            orientation = Orientation.Horizontal
                        )
                ) {
                    DoorCard(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(
                                x = swipeState.offset.value.dp
                            )
                            .width(width.dp),
                        doorModel = doorModel
                    )

                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        visible = swipeState.currentValue == 1,
                        enter = slideInHorizontally(
                            initialOffsetX = { it * 2 },
                            animationSpec = tween(100)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { it * 2 },
                            animationSpec = tween(100)
                        ),
                    ) {
                        Row {
                            IconButton(onClick = {
                                changeNameDoorModel.value = doorModel
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.Cyan
                                )
                            }

                            Crossfade(targetState = doorModel.favorites, label = "") {
                                if(it) {
                                    IconButton(onClick = { onFavorite(doorModel.id) }) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = Color.Yellow
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { onFavorite(doorModel.id) }) {
                                        Icon(
                                            imageVector = Icons.Default.FavoriteBorder,
                                            contentDescription = null,
                                            tint = Color.Yellow
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        })

    ChangeNameDialog(isShowing = changeNameDoorModel, confirm = {
        changeNameDoorModel.value?.let { door ->
            onChangeName(door, it)
        }
        changeNameDoorModel.value = null
    })
}

@Composable
private fun DoorCard(modifier: Modifier = Modifier, doorModel: DoorModel) {
    Column(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(16.dp), spotColor = Color.LightGray)
            .padding(1.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        Box {
            if (doorModel.snapshotUrl != null) {
                AsyncImage(
                    modifier = Modifier.aspectRatio(16f / 9f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(doorModel.snapshotUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }


            if (doorModel.snapshotUrl != null) {
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
        }

        Text(modifier = Modifier.padding(16.dp), text = doorModel.name, fontSize = 18.sp)
    }
}

@Composable
private fun ChangeNameDialog(
    isShowing: MutableState<DoorModel?>,
    confirm: (String) -> Unit = {}
) {
    if (isShowing.value != null) {
        Dialog(onDismissRequest = { isShowing.value = null }) {
            Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {
                var name by remember { mutableStateOf(isShowing.value?.name ?: "") }
                Column {
                    TextField(value = name, onValueChange = {
                        name = it
                    })

                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { confirm(name) }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

