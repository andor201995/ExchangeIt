package com.example.openexchange.ui.rates

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.openexchange.usecase.model.Rate
import com.example.openexchange.ui.model.ScreenUiModel

@Composable
fun ScreenContent(
    viewState: State<ScreenUiModel>,
    modifier: Modifier = Modifier,
    onCurrencySelected: (String) -> Unit,
    onAmountChanged: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        AmountTextInputBox(viewState, modifier, onAmountChanged)
        CurrencyDropdownMenuBox(viewState, modifier, onCurrencySelected)
        ExchangeRateList(viewState, modifier)
    }

}

@Composable
fun ExchangeRateList(viewState: State<ScreenUiModel>, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(viewState.value.rates.rates) {
            RateItem(it, modifier)
        }
    }
}

@Composable
fun RateItem(it: Rate, modifier: Modifier) {
    Card(
        border = BorderStroke(2.dp, Color.Black),
        modifier = modifier.background(Color.White),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .aspectRatio(1f)
                .padding(10.dp)
        ) {
            Text(
                text = "${it.currencyCode} = ${it.value}",
                modifier = modifier,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Composable
fun AmountTextInputBox(
    viewState: State<ScreenUiModel>,
    modifier: Modifier,
    onAmountChanged: (String) -> Unit
) {
    var amountText by remember { mutableStateOf(viewState.value.input.amount.toString()) }

    OutlinedTextField(
        value = amountText,
        onValueChange = {
            amountText = it
            onAmountChanged.invoke(it)
        },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = "Amount") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenuBox(state: State<ScreenUiModel>, modifier: Modifier, onClick: (String) -> Unit) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        CompositionLocalProvider(LocalTextInputService provides null) {
            OutlinedTextField(
                value = state.value.input.selectedCurrency,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor(),
                label = { Text(text = "Currency") }
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Box(modifier = Modifier.size(width = 100.dp, height = 300.dp)) {
                LazyColumn {
                    items(state.value.currency.values) { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item.code
                                )
                            },
                            onClick = {
                                expanded = false
                                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                onClick.invoke(item.code)
                            },
                        )
                    }
                }
            }
        }
    }
}