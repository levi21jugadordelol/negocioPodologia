import { BASE_URL } from "../../config/configuracion.js";
import { clienteStorage } from "../../localStorage/clienteStorage.js";

export async function enviarDeleteClienteApi(idCliente) {
  const URL_API = `${BASE_URL}/cliente/eliminar/${idCliente}`;

  try {
    const respuesta = await fetch(URL_API, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }
    clienteStorage.eliminarClientePorId(idCliente);
    console.log("🗑️ Cliente eliminado también de localStorage");
    return { success: true, id: idCliente };
  } catch (error) {
    console.error("❌ Error al eliminar cliente:", error.message);
    alert("❌ Error al eliminar cliente");
    return { error: true, mensaje: error.message };
  }
}
