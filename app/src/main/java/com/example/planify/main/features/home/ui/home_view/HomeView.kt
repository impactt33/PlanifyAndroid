package com.example.planify.main.features.home.ui.home_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.planify.main.features.home.ui.home_view.components.WeeklySchedule
import java.time.LocalDate

@Composable
fun HomeView(scaffoldPadding: PaddingValues) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(

    ) {

    }
}

// далее делать viewmodel