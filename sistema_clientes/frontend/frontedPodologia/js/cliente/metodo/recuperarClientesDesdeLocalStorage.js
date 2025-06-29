import { clienteStorage } from "../../localStorage/clienteStorage";
import { datosCliente } from "../../obteniendoDatos.js";
import { enviandoDatos } from "../../enviandoDatos.js";

const d = document;

export const recuperarClientesDesdeLocalStorage = () => {
  datosCliente.length = 0;

  const clientes = clienteStorage.obtenerClientes();
  console.log("clientes recuperados desde localStorage: ", clientes);

  datosCliente.push(...clientes);

  console.log("datos cliente actualizado: ", datosCliente);
  enviandoDatos(datosCliente);
  return datosCliente;
};
