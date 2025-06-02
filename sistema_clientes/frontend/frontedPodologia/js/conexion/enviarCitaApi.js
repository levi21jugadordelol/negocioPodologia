import { BASE_URL } from "../config/configuracion.js";
//import { CitaStorage } from "../localStorage/CitaStorage.js";
import { citaStorage } from "../localStorage/CitaStorage.js";

const URL_API = `${BASE_URL}/citas/crear`;

export async function enviarCitaApi(cita) {
  try {
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(cita),
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const citaGuardado = await respuesta.json();
    const { idCita } = citaGuardado;

    // 🟩 Combinar la cita original con el id retornado por la API
    const citaConId = { ...cita, idCita };

    // 🟩 Guardar en localStorage
    // CitaStorage.guardarCita(citaConId);
    citaStorage.guardar(citaConId);

    alert("✅ Cita guardado exitosamente");
    return { exito: true, mensaje: citaGuardado.mensaje };
  } catch (e) {
    console.error("❌ Error al enviar cita:", e.message);
    alert("❌ Error al guardar cita");
    return { error: true, mensaje: e.message };
  }
}
