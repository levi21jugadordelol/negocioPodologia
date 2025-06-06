import { clienteStorage } from "../localStorage/clienteStorage.js";
import { enviandoDatos } from "../enviandoDatos.js";
import { enviarEditClienteApi } from "./api_cliente/enviarEditClientApi.js";
import { Cliente } from "../clases/cliente.js";
const d = document;
export const capturaInfoClientEdit = async () => {
  const $form = d.getElementById("form_cliente_edit");
  console.log("el valor de $form es: ", $form);

  const id_cliente = $form.dataset.id;

  console.log(id_cliente);

  const nombre = document.getElementById("modal_nombre_edit").value.trim();
  const apellido = document.getElementById("modal_apellido_edit").value.trim();
  const correo = document.getElementById("modal_correo_edit").value.trim();
  const celular = document.getElementById("modal_celular_edit").value.trim();
  const dni = document.getElementById("modal_dni_edit").value.trim();

  // ✅ Crea instancia para validar
  let clienteJS;
  try {
    clienteJS = new Cliente(nombre, apellido, correo, celular, dni);
  } catch (error) {
    alert("❌ Datos inválidos: " + error.message);
    return;
  }

  const clienteBackend = clienteJS.toBackendJson(); // ✅ Convierte a JSON esperado

  console.log("el cliente editado es: ", clienteBackend);

  // Lógica separada: si idCliente existe → editar
  if (id_cliente) {
    await enviarEditClienteApi(id_cliente, clienteBackend);
  } else {
    console.warn("No se puede editar: no hay ID de cliente");
  }
  $form.reset();

  // Recupera todos los clientes actualizados desde localStorage:
  const clientesActualziados = clienteStorage.obtenerClientes();
  // Vuelve a renderizar la tabla con los nuevos datos:
  enviandoDatos(clientesActualziados);
};
