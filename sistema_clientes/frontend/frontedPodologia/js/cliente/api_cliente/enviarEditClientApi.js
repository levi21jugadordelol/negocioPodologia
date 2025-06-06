import { BASE_URL } from "../../config/configuracion.js";
import { clienteStorage } from "../../localStorage/clienteStorage.js";
//import { enviandoDatos } from "../../enviandoDatos.js";
export async function enviarEditClienteApi(idCliente, clienteBackend) {
  const URL_API = `${BASE_URL}/cliente/editar/${idCliente}`;
  try {
    const respuesta = await fetch(URL_API, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(clienteBackend),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const clienteGuardado = await respuesta.json(); // ⬅️
    // recibe el objeto con el id

    console.log(`Cliente editado: ${JSON.stringify(clienteGuardado)}`);

    alert("✅ Cliente editado exitosamente");
    const { idCliente } = clienteGuardado;

    console.log(clienteGuardado.idCliente);

    // ✅ Agregamos el data-id al formulario
    // ✅ 1. Guardar ID en el formulario
    const formulario = document.getElementById("form_cliente_edit");
    console.log(formulario);
    if (formulario) {
      formulario.dataset.id = idCliente;
    }

    // ✅ 2. Guardar cliente en LocalStorage
    // const clienteConId = { ...clienteBackend, idCliente };
    // guardarClienteEnLocalStorage(clienteConId);
    //clienteStorage.guardarCliente(clienteConId);
    /*console.log(
      "🟡 clienteConId antes de guardar en localStorage:",
      clienteConId
    ); */

    const clienteNormalizado = {
      nombre: clienteBackend.nombreCliente,
      apellido: clienteBackend.apellidoCliente,
      dni: clienteBackend.dniCliente,
      celular: clienteBackend.cellCliente,
      correo: clienteBackend.correoCliente,
      idCliente: idCliente,
    };

    console.log(
      "🟢 Cliente normalizado que se va a guardar:",
      clienteNormalizado
    );

    // 1. Guardamos el cliente
    clienteStorage.guardarCliente(clienteNormalizado);

    // 2. Obtenemos todos los clientes actualizados
    const todosLosClientes = clienteStorage.obtenerClientes();

    // 3. Refrescamos la tabla
    // enviandoDatos(todosLosClientes); // ⬅️ Aquí se actualiza la vista del usuario

    // 4. Confirmaciones opcionales
    console.log("📦 Clientes actualmente en localStorage:", todosLosClientes);

    // Opcional: verificar si el cliente editado está ahí
    const clienteEncontrado = todosLosClientes.find(
      (c) => c.idCliente === idCliente
    );
    if (clienteEncontrado) {
      console.log(
        "✅ Cliente editado encontrado en localStorage:",
        clienteEncontrado
      );
    } else {
      console.warn("❌ Cliente editado NO encontrado en localStorage.");
    }
    alert("✅ Cliente guardado exitosamente");

    //  return { exito: true, mensaje: clienteGuardado.mensaje };
    return idCliente;
  } catch (error) {
    console.error("❌ Error al enviar cliente:", error.message);
    alert("❌ Error al guardar cliente");
    return { error: true, mensaje: error.message };
  }
}
