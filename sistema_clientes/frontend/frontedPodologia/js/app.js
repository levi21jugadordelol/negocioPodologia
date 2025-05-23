import crearModalNuevoCliente from "./crearModalNuevoCliente.js";

document.addEventListener("DOMContentLoaded", () => {
  crearModalNuevoCliente(
    ".btn_nuevo_cliente",
    ".modal-overlay",
    ".close",
    ".enviar"
  );
});
