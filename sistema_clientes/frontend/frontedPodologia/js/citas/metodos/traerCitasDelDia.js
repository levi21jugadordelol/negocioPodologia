import { citasDelDiaApi } from "../api_cita/citasDelDiaApi.js";
import { datosCita } from "../../getDatafromCita.js";
import { addIdToTable } from "../../addIdToCita.js";
import { mapCita } from "../metodos/mapCitas.js";
import { pintarDatosDelDia } from "./pintarDatosDelDia.js";

const d = document;

export const traerCitasDelDia = async () => {
  const hoy = new Date().toISOString().split("T")[0];

  try {
    const citas = await citasDelDiaApi(hoy);
    console.log("📦 Citas del día desde API:", citas);

    const citasMapeadas = citas.map(mapCita);
    console.log("🗂️ Citas mapeadas:", citasMapeadas);

    datosCita.splice(0, datosCita.length, ...citas); // actualizar datos globales
    console.log("📌 datosCita actualizada:", datosCita);

    pintarDatosDelDia(citasMapeadas);

    return citasMapeadas;
  } catch (error) {
    console.error("❌ Error al traer citas del día:", error.message);
    return [];
  }
};
