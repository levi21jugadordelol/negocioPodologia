import { datosCita } from "../getDatafromCita.js";
import { citaStorage } from "./CitaStorage.js";
import { BASE_URL } from "../config/configuracion.js";
import { recuperarCitasDesdeLocalStorage } from "./recuperarCitasDesdeLocalStorage.js";
import { adaptarCitaBackend } from "../localStorage/adaptarCitaBackend.js";

export const cargarCitasDesdeLocalStorage = async () => {
  /* const lista = citaStorage.obtenerTodos();
  datosCita.length = 0;
  datosCita.push(...lista);
  console.log("📥 Citas reconstruidas desde localStorage:", datosCita); */
  try {
    console.log("🔄 Iniciando carga de servicios desde backend...");

    const respuesta = await fetch(`${BASE_URL}/citas/todas`, {
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    console.log("📡 Respuesta recibida:", respuesta);

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const citasRaw = await respuesta.json();
    console.log("👀 Ejemplo de cita:", citasRaw[0]);
    console.log("📦 citas recibidas del backend:", citasRaw);

    // ✅ Adaptar citas antes de guardar
    const citasAdaptadas = citasRaw.map(adaptarCitaBackend);
    console.log("✅ Citas adaptadas correctamente:", citasAdaptadas);

    //citaStorage.vaciar();
    citaStorage.vaciar();
    console.log("🧹 LocalStorage de citas vaciado");

    //citaStorage.guardar(citas);
    citaStorage.guardarTodos(citasAdaptadas);
    console.log("✅ Todos las citas fueron guardados en localStorage");

    recuperarCitasDesdeLocalStorage();
  } catch (error) {
    console.error("❌ Error al sincronizar servicios:", error.message);
    return [];
  }
};
