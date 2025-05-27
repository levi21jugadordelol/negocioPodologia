import { Cliente } from "./clases/cliente.js";
import { enviandoDatos } from "./enviandoDatos.js";
import { enviarClienteApi } from "./conexion/enviarClienteApi.js"; // Asegúrate de importar esto

export const datosCliente = [];

export const infoDatos = async () => {
  const d = document;
  const $formCliente = d.getElementById("form_cliente");

  const nombreCliente = d.getElementById("modal_nombre").value.trim();
  const apellidoCliente = d.getElementById("modal_apellido").value.trim();
  const correoCliente = d.getElementById("modal_correo").value.trim();
  const celularCliente = d.getElementById("modal_celular").value.trim();
  const dniCliente = d.getElementById("modal_dni").value.trim();

  try {
    // ✅ Usamos la clase Cliente para validar y construir correctamente
    const cliente = new Cliente(
      nombreCliente,
      apellidoCliente,
      correoCliente,
      celularCliente,
      dniCliente
    );

    datosCliente.push(cliente); // puedes guardar la instancia si lo necesitas
    console.log("Cliente agregado:", cliente);

    // ✅ Enviamos al backend usando el método toBackendJson()
    // const respuesta = await enviarClienteApi(cliente.toBackendJson());
    const respuesta = await enviarClienteApi(cliente.toBackendJson());

    console.log("Respuesta.id:", respuesta);

    if (respuesta.error) {
      alert("❌ Error al enviar al backend: " + respuesta.mensaje);
    } else {
      alert("✅ Cliente enviado exitosamente.");
      cliente.id = respuesta; // o cliente.idCliente = respuesta;
    }
  } catch (error) {
    alert("❌ Error al crear cliente: " + error.message);
  }
  console.log("📋 Lista de clientes:", datosCliente);
  $formCliente.reset();
  enviandoDatos();
};
