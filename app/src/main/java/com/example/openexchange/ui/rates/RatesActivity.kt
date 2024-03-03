package com.example.openexchange.ui.rates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.openexchange.ui.theme.OpenExchangeTheme
import com.example.openexchange.ui.viewModel.ExchangeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RatesActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: ExchangeScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenExchangeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenContent(
                        viewModel.state.collectAsState(),
                        onCurrencySelected = viewModel::onCurrencySelected,
                        onAmountChanged = viewModel::onAmountChanged
                    )
                }
            }
        }
    }
}