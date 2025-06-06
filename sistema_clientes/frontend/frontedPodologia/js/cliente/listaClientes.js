import { clienteStorage } from "../localStorage/clienteStorage.js";

//export let clientes = clienteStorage.obtenerClientes();

export function actualizarClientesEnMemoria() {
  return clienteStorage.obtenerClientes();
}
