import { BASE_URL } from "../../config/configuracion.js";

const d = document;

export const citaCanceladaApi = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas canceladas...");
    let url = `${BASE_URL}/citas/clientes?estado=CANCELADA`;
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
    const citasArray = Array.isArray(citas) ? citas : [];
    console.log(`✅ ${citasArray.length} citas recibidas`);
    return citasArray;
  } catch (error) {
    console.error("❌ Error general en citaCanceladaApi:", error);
  }
};
