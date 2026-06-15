package com.example.stockpro

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stockActual: Int
)

class StockViewModel : ViewModel() {

    val listaProductos = mutableStateListOf(
        Producto(1, "Laptop", "Laptop HP", 850.0, 10),
        Producto(2, "Mouse", "Mouse Logitech", 15.0, 3),
        Producto(3, "Teclado", "Teclado Gamer", 40.0, 2),
        Producto(4, "Monitor", "Monitor LG", 220.0, 6),
        Producto(5, "Impresora", "Impresora Epson", 180.0, 2),
        Producto(6, "Parlantes", "Parlantes USB", 25.0, 8)
    )

    fun obtenerProducto(id: Int): Producto? {
        return listaProductos.find { producto ->
            producto.id == id
        }
    }

    fun actualizarStock(id: Int, nuevoStock: Int) {
        val index = listaProductos.indexOfFirst { producto ->
            producto.id == id
        }

        if (index >= 0) {
            listaProductos[index] = listaProductos[index].copy(
                stockActual = nuevoStock.coerceAtLeast(0)
            )
        }
    }

    fun calcularValorTotalInventario(): Double {
        return listaProductos.sumOf { producto ->
            producto.precio * producto.stockActual
        }
    }

    fun obtenerProductosEnRiesgo(): List<Producto> {
        return listaProductos.filter { producto ->
            producto.stockActual < 5
        }
    }

    fun totalSinStock(): Int {
        return listaProductos.count { producto ->
            producto.stockActual == 0
        }
    }
}