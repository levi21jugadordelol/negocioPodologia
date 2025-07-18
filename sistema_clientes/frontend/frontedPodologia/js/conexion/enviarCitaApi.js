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
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(cita),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const citaGuardado = await respuesta.json();
    const { idCita } = citaGuardado;

    // 💾 Guarda sólo el ID si eso necesitas, o toda la respuesta real del backend
    // citaStorage.guardar({ ...citaGuardado });

    alert("✅ Cita guardada exitosamente");

    return {
      exito: true,
      idCita,
      mensaje: citaGuardado.mensaje,
    };
  } catch (e) {
    console.error("❌ Error al enviar cita:", e.message);
    alert("❌ Error al guardar cita");
    return { error: true, mensaje: e.message };
  }
}
