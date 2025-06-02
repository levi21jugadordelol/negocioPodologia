import { datosCita } from "../getDatafromCita.js";
import { citaStorage } from "./CitaStorage.js";

export const cargarCitasDesdeLocalStorage = () => {
  const lista = citaStorage.obtenerTodos();
  datosCita.length = 0;
  datosCita.push(...lista);
  console.log("📥 Citas reconstruidas desde localStorage:", datosCita);
};
