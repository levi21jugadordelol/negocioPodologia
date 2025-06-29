import { BASE_URL } from "../../config/configuracion.js";

const d = document;
export async function comenzarTollenarClientPorDiaApi(fecha) {
  console.log("📅 Fecha enviada al backend:", fecha);
  //aca hacemos un fect llamando al endpoint, supongo que como parametro debo ponerle una fecha
  const URL_API = `${BASE_URL}/cliente/por-dia?fecha=${fecha}`;

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
    // clienteStorage.eliminarClientePorId(idCliente);
    // console.log("🗑️ Cliente eliminado también de
    // const data = await respuesta.json();
    const data = await respuesta.json();
    console.log("📡 Clientes recibidos del backend por dia:", data);

    return data; // ✅ DEVUELVE los clientes al caller (muy importante)localStorage");
  } catch (error) {
    console.error("❌ Error al traer  cliente del dia:", error.message);
    alert("❌ Error al traer clientes del dia");
    //return { error: true, mensaje: error.message };
    return [];
  }
}
