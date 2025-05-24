import crearModalNuevoCliente from "./crearModalNuevoCliente.js";
import { tableCita } from "./windowForCite.js";
import { tableClient } from "./windowForClient.js";

document.addEventListener("DOMContentLoaded", () => {
  crearModalNuevoCliente(
    ".btn_nuevo_cliente",
    ".modal-overlay",
    ".close",
    ".enviar"
  );
  tableCita("#btn-citas", "#vista-citas");
  tableClient("#btn-clientes", "#vista-clientes");
});
