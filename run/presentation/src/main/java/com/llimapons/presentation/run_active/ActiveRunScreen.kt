@file:OptIn(ExperimentalMaterial3Api::class)

package com.llimapons.presentation.run_active

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.llimapons.core.presentation.designsystem.RunmasterTheme
import com.llimapons.core.presentation.designsystem.StartIcon
import com.llimapons.core.presentation.designsystem.StopIcon
import com.llimapons.core.presentation.designsystem.components.RunmasterFloatingActionButton
import com.llimapons.core.presentation.designsystem.components.RunmasterScaffold
import com.llimapons.core.presentation.designsystem.components.RunmasterToolbar
import com.llimapons.presentation.run_active.components.RunDataCard
import com.llimapons.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoot(
    onBackClick: () -> Unit = {},
    viewModel: ActiveRunViewModel = koinViewModel()
) {
    ActiveRunEventScreen(
        state = viewModel.state,
        onAction = {action ->
            when(action){
                ActiveRunAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ActiveRunEventScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    RunmasterScaffold(
        withGradient = false,
        topAppBar = {
            RunmasterToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {
            RunmasterFloatingActionButton(
                icon = if(state.shouldTrack) {
                    StopIcon
                } else{
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if(state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                }else{
                    stringResource(id = R.string.start_run)
                }
            )
        },
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        ){
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(paddings)
            )
        }

    }
}

@Preview
@Composable
private fun ActiveRunEventScreenPreview() {
    RunmasterTheme {
        ActiveRunEventScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}