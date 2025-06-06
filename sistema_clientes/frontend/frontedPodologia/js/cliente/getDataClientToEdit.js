//import { clientes } from "./listaClientes.js";
import { actualizarClientesEnMemoria } from "./listaClientes.js";

const d = document;

export const getDataClientToEdit = (idCliente) => {
  const $form = document.getElementById("form_cliente_edit");
  console.log("el valor del formulario en el html es: ", $form);
  if (!$form) return;

  /* const clientes = clienteStorage.obtenerClientes();
  console.log("los clientes obtenidos son : ", clientes); */

  // Busca el cliente por idCliente
  /* const cliente = clientes.find((c) => String(c.id) === String(idCliente)); */

  const cliente = actualizarClientesEnMemoria().find(
    (c) => String(c.id) === String(idCliente)
  );

  if (!cliente) {
    console.warn("Cliente no encontrado con id:", idCliente);
    return;
  }

  console.log("el id cliente es: ", cliente.idCliente);

  // Asignamos los datos al formulario
  $form.dataset.id = cliente.id;

  console.log("$form.elements:", $form.elements);
  console.log(
    "$form.elements['nombreCliente']:",
    $form.elements["nombreCliente"]
  );

  $form.elements["nombreCliente"].value = cliente.nombre || "";
  $form.elements["apellidoCliente"].value = cliente.apellido || "";
  $form.elements["correoCliente"].value = cliente.correo || "";
  $form.elements["cellCliente"].value = cliente.celular || "";
  $form.elements["dniCliente"].value = cliente.dni || "";

  console.log("📝 Cliente cargado para edición:", cliente);
  console.log("Formulario dataset id:", $form.dataset.id);
  console.log("Nombre en input:", $form.elements["nombreCliente"].value);
};
