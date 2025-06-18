import { datosServicio } from "../../getDateServiceFromModal.js";
import { servicioStorage } from "../../localStorage/servicioStorage.js";
import { sendDataToTablaCite } from "../../sendDataToTablaCite.js";

const d = document;

export function recuperarServiciosDesdeLocalStorage() {
  datosServicio.length = 0;

  const servicios = servicioStorage.obtenerServicios();
  console.log("📦 servicios recuperados desde localStorage:", servicios);

  datosServicio.push(...servicios);

  console.log("datosServicio actual es: ", datosServicio);

  sendDataToTablaCite();
  return datosServicio;
}
