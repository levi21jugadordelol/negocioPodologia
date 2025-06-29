import { imprimirCitasFinalizadas } from "../metodo/imprimirCitasFinalizadas.js";
import { BASE_URL } from "../../config/configuracion.js";

const d = document;

export const getCiteFinished = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas finalizadas...");

    let url = `${BASE_URL}/citas/clientes?estado=ATENDIDA`;
    if (dni) url += `&dni=${encodeURIComponent(dni)}`;
    else if (nombre) url += `&nombre=${encodeURIComponent(nombre)}`;
    console.log("🧭 URL: ", url);

    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`⛔ HTTP ${response.status}: ${response.statusText}`);
    }

    const citas = await response.json();

    console.log(`✅ [getCitePending] ${citas.length} citas recibidas`);
    console.table(citas);

    imprimirCitasFinalizadas(citas);
  } catch (error) {
    console.error("❌ Error general en getCitePending:", error);
  }
};
