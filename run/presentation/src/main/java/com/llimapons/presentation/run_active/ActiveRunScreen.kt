@file:OptIn(ExperimentalMaterial3Api::class)

package com.llimapons.presentation.run_active

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.llimapons.core.presentation.designsystem.RunmasterTheme
import com.llimapons.core.presentation.designsystem.StartIcon
import com.llimapons.core.presentation.designsystem.StopIcon
import com.llimapons.core.presentation.designsystem.components.RunmasterDialog
import com.llimapons.core.presentation.designsystem.components.RunmasterFloatingActionButton
import com.llimapons.core.presentation.designsystem.components.RunmasterOutlinenButton
import com.llimapons.core.presentation.designsystem.components.RunmasterScaffold
import com.llimapons.core.presentation.designsystem.components.RunmasterToolbar
import com.llimapons.presentation.run_active.components.RunDataCard
import com.llimapons.presentation.util.hasLocationPermission
import com.llimapons.presentation.util.hasNotificationPermission
import com.llimapons.presentation.util.hasPermission
import com.llimapons.presentation.util.shouldShowLocationPermissionRationale
import com.llimapons.presentation.util.shouldShowNotificationPermissionRationale
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
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCoarseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRational = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCoarseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRationale = showNotificationRational
            )
        )
    }

    LaunchedEffect(key1 = true){
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRational = activity.shouldShowNotificationPermissionRationale()
        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )

        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRationale = showNotificationRational
            )
        )

        if(!showLocationRationale && !showNotificationRational){
            permissionLauncher.requestRuniquePermissions(context)
        }
    }

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

    if (state.showLocationRationale || state.showNotificationRationale){
        RunmasterDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Dissmis not allowed */},
            description = when {
                state.showLocationRationale && state.showNotificationRationale ->{
                    stringResource(id = R.string.location_notification_rationale)
                }
                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }
                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            primaryButton = {
                RunmasterOutlinenButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.DismissRationalDialog)
                        permissionLauncher.requestRuniquePermissions(context)
                    }
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRuniquePermissions(
    context: Context
){
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val notificationPermissions = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else emptyArray()

    when{
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermissions)
        }
        !hasLocationPermission -> {
            launch(locationPermissions)
        }
        !hasNotificationPermission -> {
            launch(notificationPermissions)
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