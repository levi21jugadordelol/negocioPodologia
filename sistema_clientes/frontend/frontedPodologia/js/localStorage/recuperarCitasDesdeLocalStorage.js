import { addIdToTable } from "../addIdToCita.js";
import { datosCita } from "../getDatafromCita.js";
import { sendDataToTablaCite } from "../sendDataToTablaCite.js";

import { citaStorage } from "./CitaStorage.js";

export function recuperarCitasDesdeLocalStorage() {
  datosCita.length = 0;

  const citas = citaStorage.obtenerTodos();
  console.log("✔️ Citas válidas recuperadas:", citas);

  datosCita.push(...citas);
  console.log("📌 datosCita actualizado:", datosCita);

  sendDataToTablaCite();
  return datosCita;
}
