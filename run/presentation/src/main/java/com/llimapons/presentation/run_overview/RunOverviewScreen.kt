@file:OptIn(ExperimentalMaterial3Api::class)

package com.llimapons.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.llimapons.core.presentation.designsystem.AnalyticsIcon
import com.llimapons.core.presentation.designsystem.LogoIcon
import com.llimapons.core.presentation.designsystem.LogoutIcon
import com.llimapons.core.presentation.designsystem.RunIcon
import com.llimapons.core.presentation.designsystem.RunmasterTheme
import com.llimapons.core.presentation.designsystem.components.RunmasterFloatingActionButton
import com.llimapons.core.presentation.designsystem.components.RunmasterScaffold
import com.llimapons.core.presentation.designsystem.components.RunmasterToolbar
import com.llimapons.core.presentation.designsystem.components.ui.DropDownItem
import com.llimapons.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    onStartRunClick: () -> Unit = {},
    viewModel: RunOverviewViewModel = koinViewModel()
) {
    RunOverviewScreen(
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    RunmasterScaffold(
        topAppBar = {
            RunmasterToolbar(
                showBackButton = false,
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.runmaster),
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    )
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(35.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RunmasterFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RunmasterTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}