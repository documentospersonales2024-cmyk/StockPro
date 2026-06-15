package com.example.stockpro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.NavController
import com.example.stockpro.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PantallaCatalogo(
    operario: String,
    viewModel: StockViewModel,
    onProductClick: (Int) -> Unit,
    onReporteClick: () -> Unit
) {

    var soloCriticos by remember {
        mutableStateOf(false)
    }

    val productosAMostrar = if (soloCriticos) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.listaProductos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Operario: $operario",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = { soloCriticos = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!soloCriticos)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Todo")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { soloCriticos = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (soloCriticos)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Críticos")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(productosAMostrar) { producto ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                onProductClick(producto.id)
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {

                            Text(text = producto.nombre)

                            Text(text = "Precio: $${producto.precio}")

                            Text(
                                text = "Stock: ${producto.stockActual}",
                                color = if (producto.stockActual < 5)
                                    Color.Red
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onReporteClick
            ) {
                Text("Ver Reporte")
            }
        }
    }
}
