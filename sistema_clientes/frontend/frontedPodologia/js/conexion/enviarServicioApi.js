import { BASE_URL } from "../config/configuracion.js";

const URL_API_SERVICE = `${BASE_URL}/servicio/crear`;

export async function enviarCitaApi(servicioData) {
  try {
    console.log("➡️ Enviando al backend:", JSON.stringify(servicioData));
    const respuesta_service = await fetch(URL_API_SERVICE, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(servicioData),
    });
    if (!respuesta_service.ok) {
      throw new Error(`Error del servidor: ${respuesta_service.status}`);
    }

    const citaGuardada = await respuesta_service.json(); // ⬅️ recibe el objeto con el id
    const { idCita } = citaGuardada;

    console.log(citaGuardada.idCita);

    return { exito: true, mensaje: citaGuardada.mensaje };
  } catch (error) {
    console.error("❌ Error al enviar cliente:", error.message);
    alert("❌ Error al guardar cliente");
    return { error: true, mensaje: error.message };
  }
}
