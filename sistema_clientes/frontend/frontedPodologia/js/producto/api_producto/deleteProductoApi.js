import { BASE_URL } from "../../config/configuracion.js";
const d = document;

export const deleteProductoApi = async (idProducto) => {
  try {
    const respuesta = await fetch(
      `${BASE_URL}/producto/eliminar/${idProducto}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${sessionStorage.token}`,
        },
      }
    );

    if (!respuesta.ok) {
      throw new Error("No se pudo eliminar el producto del backend");
    }

    console.log(`🗑️ Producto ${idProducto} eliminado del backend`);
    return true;
  } catch (error) {
    console.error("❌ Error al eliminar del backend:", error.message);
    return false;
  }
};
