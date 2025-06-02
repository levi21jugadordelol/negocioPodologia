import { datosCliente } from "../obteniendoDatos.js";
//import { ClienteStorage } from "./clienteStorage.js";
import { clienteStorage } from "./clienteStorage.js";

export const cargarClientesDesdeLocalStorage = () => {
  //asegura una única instancia.
  const lista = clienteStorage.obtenerClientes();

  datosCliente.length = 0;
  datosCliente.push(...lista);

  console.log("📥 Clientes cargados desde localStorage:", datosCliente);
};
