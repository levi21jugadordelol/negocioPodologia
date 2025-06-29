import { BASE_URL } from "../../config/configuracion.js";
import { imprimirCitasCanceladas } from "../metodo/imprimirCitasCanceladas.js";

const d = document;

export const getCiteCancelado = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas canceladas...");
    let url = `${BASE_URL}/citas/clientes?estado=CANCELADA`;
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

    // imprimiCitasCanceladas(citas);
    imprimirCitasCanceladas(citas);
  } catch (error) {
    console.error("❌ Error general en getCitePending:", error);
  }
};
