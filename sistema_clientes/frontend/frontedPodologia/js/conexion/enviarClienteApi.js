import { BASE_URL } from "../config/configuracion.js";
//import { clienteStorage } from "../localStorage/clienteStorage.js";
import { clienteStorage } from "../localStorage/clienteStorage.js";

const URL_API = `${BASE_URL}/cliente/crear`;

export async function enviarClienteApi(cliente) {
  try {
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(cliente),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const clienteGuardado = await respuesta.json(); // ⬅️ recibe el objeto con el id

    const { idCliente } = clienteGuardado;

    console.log(clienteGuardado.idCliente);

    // ✅ Agregamos el data-id al formulario
    // ✅ 1. Guardar ID en el formulario
    const formulario = document.getElementById("form_cliente");
    if (formulario) {
      formulario.dataset.id = idCliente;
    }

    // ✅ 2. Guardar cliente en LocalStorage
    const clienteConId = { ...cliente, idCliente };
    // guardarClienteEnLocalStorage(clienteConId);
    clienteStorage.guardarCliente(clienteConId);

    console.log(clienteGuardado.mensaje);
    alert("✅ Cliente guardado exitosamente");

    //  return { exito: true, mensaje: clienteGuardado.mensaje };
    return idCliente;
  } catch (error) {
    console.error("❌ Error al enviar cliente:", error.message);
    alert("❌ Error al guardar cliente");
    return { error: true, mensaje: error.message };
  }
}
