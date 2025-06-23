import { BASE_URL } from "../../config/configuracion.js";

const d = document;

export const citasDelDiaApi = async (fecha) => {
  const URL_API = `${BASE_URL}/citas/por-dia?fecha=${fecha}`;

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
    console.log("📡 Citas recibidos del backend por dia:", data);

    return data;
  } catch (error) {
    console.error("❌ Error al traer  citas del dia:", error.message);
    alert("❌ Error al traer citas del dia");
    //return { error: true, mensaje: error.message };
    return [];
  }
};
