import { BASE_URL } from "../config/configuracion.js";

const URL_API = `${BASE_URL}/cliente/crear`;

export async function enviarClienteApi(cliente) {
  try {
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
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
    const formulario = document.getElementById("form_cliente");
    if (formulario) {
      formulario.dataset.id = idCliente;
    }

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
