import { BASE_URL } from "../../config/configuracion.js";
//import { clienteStorage } from "../../localStorage/clienteStorage.js";
import { citaStorage } from "../../localStorage/CitaStorage.js";
//import { gestionarCitaEditada } from "../gestionarCitaEditadaStorage.js";
//import { enviandoDatos } from "../../enviandoDatos.js";
import { gestionarCitaEditadaStorage } from "../gestionarCitaEditadaStorage.js";
export async function enviarEditCitaApi(id_cita, citaBackend) {
  console.log("cita ingresando al api: ", citaBackend);

  const URL_API = `${BASE_URL}/citas/editar/${id_cita}`;
  try {
    const respuesta = await fetch(URL_API, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(citaBackend),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const citaGuardado = await respuesta.json(); // ⬅️
    // recibe el objeto con el id

    console.log(`Cita editado: ${JSON.stringify(citaGuardado)}`);

    alert("✅ Cita editado exitosamente");
    const { id_cita } = citaGuardado;

    //console.log(clienteGuardado.idCliente);
    console.log(citaGuardado.id_cita);

    // ✅ Lógica de localStorage separada
    gestionarCitaEditadaStorage(id_cita, citaBackend);

    return id_cita;
  } catch (error) {
    console.error("❌ Error al enviar cliente:", error.message);
    alert("❌ Error al guardar cita");
    return { error: true, mensaje: error.message };
  }
}
