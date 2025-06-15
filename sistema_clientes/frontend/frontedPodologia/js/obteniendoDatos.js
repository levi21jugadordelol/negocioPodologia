import { Cliente } from "./clases/cliente.js";
import { enviandoDatos } from "./enviandoDatos.js";
import { enviarClienteApi } from "./conexion/enviarClienteApi.js"; // Asegúrate de importar esto
import { cargarClientesDesdeLocalStorage } from "./localStorage/cargarClientesDesdeLocalStorage.js";
import { cargarClientesDelDia } from "./cliente/cargarClienteDelDia.js";

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
      // 🔁 Volvemos a leer desde localStorage para estar seguros
      // Ya no uses "listaClientes"
      const clientesActuales =
        JSON.parse(localStorage.getItem("clientes")) || [];
      clientesActuales.push(cliente); // sin ID todavía
      localStorage.setItem("clientes", JSON.stringify(clientesActuales));
      // cargarClientesDesdeLocalStorage(datosCliente);
      //enviandoDatos(datosCliente);
      await cargarClientesDelDia();
      console.log("✔️ Clientes del día recargados"); // 👈
    }
  } catch (error) {
    alert("❌ Error al crear cliente: " + error.message);
  }
  console.log("📋 Lista de clientes:", datosCliente);
  $formCliente.reset();
  // enviandoDatos();
};
