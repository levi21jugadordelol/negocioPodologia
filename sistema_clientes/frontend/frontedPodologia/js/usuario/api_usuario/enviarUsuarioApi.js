import { BASE_URL } from "../../config/configuracion.js";

const URL_API = `${BASE_URL}/api/user`;
const d = document;

export async function enviarUsuarioApi(usuario) {
  try {
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(usuario),
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }
    const mensaje = await respuesta.json(); // como devuelves solo texto (String)
    console.log("✅ Respuesta del backend:", mensaje);
    return { exito: true, mensaje };
  } catch (error) {
    console.error("❌ Error al enviar usuario:", error.message);
    alert("❌ Error al guardar usuario");
    return { error: true, mensaje: error.message };
  }
}
