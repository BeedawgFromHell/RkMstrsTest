package com.example.rkmstrstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rkmstrstest.components.TopAppBar
import com.example.rkmstrstest.ui.theme.RkMstrsTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RkMstrsTestTheme {
                MainScreen(
                    isRefreshing = viewModel.isRefreshing,
                    onRefresh = viewModel::refreshData
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun MainScreen(
    isRefreshing: State<Boolean> = remember { mutableStateOf(false) },
    onRefresh: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        2
    }
    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing.value, onRefresh = onRefresh)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                pagerState = pagerState
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .pullRefresh(refreshState)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalPager(
                modifier = Modifier,
                state = pagerState
            ) {

            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = isRefreshing.value,
                state = refreshState,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MainScreen()
}