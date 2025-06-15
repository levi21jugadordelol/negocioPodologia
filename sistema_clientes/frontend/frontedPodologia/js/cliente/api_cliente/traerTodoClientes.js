import { BASE_URL } from "../../config/configuracion.js";

const d = document;

export async function traerTodoClientes() {
  const URL_API = `${BASE_URL}/cliente/todas`;

  try {
    const respuesta = await fetch(URL_API, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }
    const data = await respuesta.json();
    console.log("📡 Clientes totales recibidos del backend:", data);
    return data;
  } catch (error) {
    console.error(
      "❌ Error al traer todos los  cliente de la DB:",
      error.message
    );
    alert("❌ Error al traer clientes de la db");
    return [];
  }
}
