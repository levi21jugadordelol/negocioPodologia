import { comenzarTollenarClientPorDiaApi } from "./api_cliente/comenzarTollenarClientPorDia.js";
import { mapCliente } from "./metodo/mapCliente.js";
import { enviandoDatos } from "../enviandoDatos.js";
import { datosCliente } from "../obteniendoDatos.js";

export async function cargarClientesDelDia() {
  const hoy = new Date().toISOString().split("T")[0];

  try {
    const clientes = await comenzarTollenarClientPorDiaApi(hoy);
    console.log("📦 Clientes del día desde API:", clientes);

    const clientesMapeados = clientes.map(mapCliente);
    console.log("clientes mapeados: ", clientesMapeados);

    // 👉 Esto actualiza la lista compartida en memoria
    datosCliente.splice(0, datosCliente.length, ...clientes);
    console.log("💾 datosCliente actualizado:", datosCliente);

    enviandoDatos(clientesMapeados);
    return clientesMapeados;
  } catch (err) {
    console.error("❌ Error al cargar clientes por fecha:", err.message);
    alert("❌ No se pudieron cargar los clientes del día.");
    return [];
  }
}
