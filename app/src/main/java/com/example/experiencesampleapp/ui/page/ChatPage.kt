package com.example.experiencesampleapp.ui.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.experiencesampleapp.ui.componments.MessageBox

@Composable
fun ChatPage(
    navController: NavController,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        items(10) { MessageBox("index: $it") }
    }

    LaunchedEffect(Unit) {
        lazyListState.scrollToItem(index = 9) // Scroll to the last item (index 49)
    }
}