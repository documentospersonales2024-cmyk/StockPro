package com.example.stockpro

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockpro.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val stockViewModel: StockViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            PantallaIngreso(navController = navController)
        }


        composable(
            route = "catalogo/{nombre}",
            arguments = listOf(navArgument("nombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre").orEmpty()

            PantallaCatalogo(
                operario = nombre,
                viewModel = stockViewModel,
                onProductClick = { id ->
                    navController.navigate("editar/$id")
                },
                onReporteClick = {
                    navController.navigate("reporte")
                }
            )
        }


        composable(
            route = "editar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: -1

            PantallaEdicion(
                productoId = id,
                viewModel = stockViewModel,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        // --- REPORTE ---
        composable("reporte") {
            PantallaReporte(
                viewModel = stockViewModel,
                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}