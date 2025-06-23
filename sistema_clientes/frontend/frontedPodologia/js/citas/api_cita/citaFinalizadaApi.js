import { BASE_URL } from "../../config/configuracion.js";

import { renderizarTablaCitas } from "../metodos/renderizarTabla_citas.js";

const d = document;

export const citaFinalizada = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas finalizadas...");

    // Construir URL con filtros opcionales
    let url = `${BASE_URL}/citas/clientes?estado=ATENDIDA`;
    if (dni) url += `&dni=${encodeURIComponent(dni)}`;
    else if (nombre) url += `&nombre=${encodeURIComponent(nombre)}`;

    console.log("valor de la url de atendida es: ", url);

    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    const citas = await response.json();
    console.log("📦 JSON parseado:", citas);
    // Asegurarte de que citas sea siempre un array
    const citasArray = Array.isArray(citas) ? citas : [];
    console.log(
      `📋 Total de citas finalizadas recibidas: ${citasArray.length}`
    );
    console.log("📦 Arreglo final de citas:", citasArray);

    renderizarTablaCitas(citasArray);
  } catch (error) {
    console.error("❌ Error general en citaFinalizadaApi:", error);
  }
};
