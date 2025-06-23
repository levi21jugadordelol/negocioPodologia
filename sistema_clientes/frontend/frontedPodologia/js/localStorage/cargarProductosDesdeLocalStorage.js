import { productoStorage } from "./productoStorage.js";

import { BASE_URL } from "../config/configuracion.js";
import { recuperarProductosDesdeLocalStorage } from "../producto/recuperarProductosDesdeLocalStorage.js";

export async function cargarProductosDesdeLocalStorage() {
  try {
    console.log("🔄 Iniciando carga de productos desde backend...");

    const respuesta = await fetch(`${BASE_URL}/producto/listar`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });

    console.log("📡 Respuesta recibida:", respuesta);

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const productos = await respuesta.json();
    console.log("📦 Productos recibidos del backend:", productos);

    productoStorage.vaciarProductos();
    console.log("🧹 LocalStorage de productos vaciado");

    productoStorage.guardarTodosLosProductos(productos);
    console.log("✅ Todos los productos fueron guardados en localStorage");

    recuperarProductosDesdeLocalStorage();
    return productos;
  } catch (error) {
    console.error("❌ Error al sincronizar productos:", error.message);
    return [];
  }
}
