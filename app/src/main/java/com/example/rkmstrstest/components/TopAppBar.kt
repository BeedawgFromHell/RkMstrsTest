package com.example.rkmstrstest.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState { 2 },
    onPageSelection: (Int) -> Unit = {},
) {
    Column(modifier = modifier) {
        CenterAlignedTopAppBar(title = {
            Text(text = "Мой дом")
        })

        TabRow(modifier = Modifier.offset(y = (-1).dp),
            selectedTabIndex = pagerState.currentPage,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(
                            tabPositions[pagerState.currentPage]
                        )
                        .background(Color.Cyan)
                        .fillMaxWidth()
                        .fillMaxHeight(0.05f)
                )
            }
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                text = {
                    Text(text = "Камеры")
                },
                onClick = { onPageSelection(0) })
            Tab(
                selected = pagerState.currentPage == 1,
                text = {
                    Text(text = "Двери")
                }, onClick = { onPageSelection(1) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun Preview() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar()
    }
}