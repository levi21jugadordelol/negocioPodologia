import { BASE_URL } from "../config/configuracion.js";

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

    alert("✅ Cita guardado exitosamente");
    return { exito: true, mensaje: citaGuardado.mensaje };
  } catch (e) {
    console.error("❌ Error al enviar cita:", e.message);
    alert("❌ Error al guardar cita");
    return { error: true, mensaje: e.message };
  }
}
