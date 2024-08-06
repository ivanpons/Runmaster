package com.llimapons.analytics.analytics_feature

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import com.llimapons.analytics.data.di.analyticsDataModule
import com.llimapons.analytics.presentation.AnalyticsDashboardScreenRoot
import com.llimapons.analytics.presentation.di.analyticsPresentationModule
import com.llimapons.core.presentation.designsystem.RunmasterTheme
import org.koin.core.context.loadKoinModules

class AnalyticsActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(listOf(
            analyticsDataModule,
            analyticsPresentationModule
        ))
        SplitCompat.installActivity(this)

        setContent {
            RunmasterTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "analytics_dashboard"){
                    composable("analytics_dashboard"){
                        AnalyticsDashboardScreenRoot(
                            onBackClick = {
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}