package com.example.stockpro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEdicion(
    productoId: Int,
    viewModel: StockViewModel,
    onVolverClick: () -> Unit
) {

    val producto = viewModel.obtenerProducto(productoId)

    var stockTemporal by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(productoId, producto) {
        producto?.let {
            stockTemporal = it.stockActual
        }
    }

    if (producto != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Stock Actual",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = stockTemporal.toString(),
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Button(
                        onClick = {
                            if (stockTemporal > 0) {
                                stockTemporal--
                            }
                        },
                        enabled = stockTemporal > 0
                    ) {
                        Text("-1")
                    }

                    Button(
                        onClick = {
                            stockTemporal++
                        }
                    ) {
                        Text("+1")
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.actualizarStock(
                        productoId,
                        stockTemporal
                    )
                    onVolverClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Guardar y Volver")
            }
        }

    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Producto no encontrado"
            )
        }
    }
}