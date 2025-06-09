//import { BASE_URL } from "../config/configuracion.js";
import { BASE_URL } from "../config/configuracion.js";

const URL_API_PRODUCTO = `${BASE_URL}/producto/crear`;

export async function enviarProductoApi(productoData) {
  try {
    console.log(
      "📦 Enviando producto al backend:",
      JSON.stringify(productoData)
    );

    const respuesta = await fetch(URL_API_PRODUCTO, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(productoData),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const mensaje = await respuesta.text(); // como devuelves solo texto (String)
    console.log("✅ Respuesta del backend:", mensaje);

    return { exito: true, mensaje };
  } catch (error) {
    console.error("❌ Error al enviar producto:", error.message);
    alert("❌ Error al guardar producto");
    return { error: true, mensaje: error.message };
  }
}
