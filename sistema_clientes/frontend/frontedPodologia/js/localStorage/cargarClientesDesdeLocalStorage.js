//import { datosCliente } from "../obteniendoDatos.js";
//import { ClienteStorage } from "./clienteStorage.js";
import { clienteStorage } from "./clienteStorage.js";

import { BASE_URL } from "../config/configuracion.js";

export async function cargarClientesDesdeLocalStorage() {
  try {
    const respuesta = await fetch(`${BASE_URL}/cliente/todas`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }
    const clientes = await respuesta.json();

    clienteStorage.vaciarClientes(); // Limpia primero

    clientes.forEach((cliente) => clienteStorage.guardarCliente(cliente));

    console.log("📥 Clientes actualizados desde backend a localStorage");
    return clientes;
  } catch (e) {
    console.error("❌ Error al sincronizar clientes:", e.message);
    return [];
  }
}
