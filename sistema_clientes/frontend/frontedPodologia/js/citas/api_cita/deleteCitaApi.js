import { BASE_URL } from "../../config/configuracion.js";
const d = document;

export const deleteCitaApi = async (idCita) => {
  try {
    const respuesta = await fetch(`${BASE_URL}/citas/eliminar/${idCita}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });

    if (!respuesta.ok) {
      throw new Error("No se pudo eliminar la cita del backend");
    }

    console.log(`🗑️ Producto ${idCita} eliminado del backend`);
    return true;
  } catch (error) {
    console.error("❌ Error al eliminar del backend:", error.message);
    return false;
  }
};
