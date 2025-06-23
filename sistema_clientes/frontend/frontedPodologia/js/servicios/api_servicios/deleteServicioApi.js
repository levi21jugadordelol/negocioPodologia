import { BASE_URL } from "../../config/configuracion.js";

const d = document;

export const deleteServicioApi = async (idServicio) => {
  try {
    const respuesta = await fetch(
      `${BASE_URL}/servicio/eliminar/${idServicio}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${sessionStorage.token}`,
        },
      }
    );

    if (!respuesta.ok) {
      const errorBody = await respuesta.json();
      const mensaje = errorBody?.message || "Error desconocido del backend";
      return { success: false, mensaje };
    }

    console.log(`🗑️ Producto ${idServicio} eliminado del backend`);
    return { success: true, mensaje: "Servicio eliminado correctamente." };
  } catch (error) {
    console.error("❌ Error al eliminar del backend:", error.message);
    return { success: false, mensaje: error.message };
  }
};
