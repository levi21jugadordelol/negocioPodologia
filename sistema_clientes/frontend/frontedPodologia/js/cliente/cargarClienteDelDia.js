import { comenzarTollenarClientPorDiaApi } from "./api_cliente/comenzarTollenarClientPorDia.js";
import { mapCliente } from "./metodo/mapCliente.js";
import { enviandoDatos } from "../enviandoDatos.js";
import { datosCliente } from "../obteniendoDatos.js";

export async function cargarClientesDelDia() {
  function getFechaLocalYYYYMMDD() {
    const hoy = new Date();
    hoy.setMinutes(hoy.getMinutes() - hoy.getTimezoneOffset()); // 🔧 Corrige a local
    return hoy.toISOString().split("T")[0];
  }

  const hoy = getFechaLocalYYYYMMDD(); // 🧠 Este es el que debes mandar al backend

  try {
    const clientes = await comenzarTollenarClientPorDiaApi(hoy);
    console.log("📦 Clientes del día desde API:", clientes);
    console.log("fecha de que ingreso cliente: ", hoy);

    const clientesMapeados = clientes.map(mapCliente);
    console.log("clientes mapeados: ", clientesMapeados);

    // 👉 Esto actualiza la lista compartida en memoria
    datosCliente.splice(0, datosCliente.length, ...clientes);
    console.log("💾 datosCliente actualizado:", datosCliente);

    const totalClientesDia = clientesMapeados.length;
    console.log("el total de cleintes del dia seria : ", totalClientesDia);

    const $totalClienteDom = document.getElementById("total-clientes");
    $totalClienteDom.textContent = totalClientesDia;

    enviandoDatos(clientesMapeados);
    return clientesMapeados;
  } catch (err) {
    console.error("❌ Error al cargar clientes por fecha:", err.message);
    alert("❌ No se pudieron cargar los clientes del día.");
    return [];
  }
}
