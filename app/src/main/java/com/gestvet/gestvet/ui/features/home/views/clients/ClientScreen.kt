package com.gestvet.gestvet.ui.features.home.views.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gestvet.gestvet.core.extensions.isScrolled
import com.gestvet.gestvet.ui.features.components.AnimatedAddFab
import com.gestvet.gestvet.ui.features.components.EmptyContent
import com.gestvet.gestvet.R
import com.gestvet.gestvet.ui.features.home.viewmodels.clients.ClientViewModel

@Composable
fun ClientScreen(
    clientViewModel: ClientViewModel,
    navigateToCreate: () -> Unit,
    navigateToDetails: (Long) -> Unit
) {
    val clients by clientViewModel.clients.collectAsState(initial = emptyList())
    val listState = rememberLazyListState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (button, emptyScreen) = createRefs()
            if (clients.isEmpty()) {
                EmptyContent()
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(emptyScreen) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    items(clients, key = { it.id }) {
                        ItemClient(
                            supportingText = it.name.toString(),
                            headlineText = it.lastname.toString()
                        ) {
                            navigateToDetails(it.id)
                        }
                    }
                }
            }
            AnimatedAddFab(
                modifier = Modifier.constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                visible = listState.isScrolled
            ) {
                navigateToCreate()
            }
        }
    }
}

@Composable
fun ItemClient(
    supportingText: String,
    headlineText: String,
    navigateToDetails: () -> Unit
) {
    Card(
        modifier = Modifier.padding(6.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        ListItem(
            supportingContent = { Text(text = supportingText) },
            headlineContent = { Text(text = headlineText) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null
                )
            },
            trailingContent = {
                IconButton(onClick = { navigateToDetails() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(id = R.string.details)
                    )
                }
            }
        )
    }
}