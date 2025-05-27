import crearModalNuevoCliente from "./crearModalNuevoCliente.js";
import { createModalService } from "./makeModalNewService.js";

import { sendClientToCita } from "./sendInfoToTableCite.js";
import { tableCita } from "./windowForCite.js";
import { tableClient } from "./windowForClient.js";
import { invocateDivServicio } from "./windowToServicio.js";
import { infoCita } from "./saveDate.js";

document.addEventListener("DOMContentLoaded", () => {
  crearModalNuevoCliente(
    ".btn_nuevo_cliente",
    ".modal-overlay",
    ".close",
    ".enviar"
  );
  tableCita("#btn-citas", "#vista-citas");
  sendClientToCita(".click_createCite", "#vista-citas", "#vista-clientes"); //click_createCite, button creado en sendInfoToTable y vista-citas, id dentro del html para el div donde esta citas
  tableClient("#btn-clientes", "#vista-clientes");
  invocateDivServicio("#btn-servicios", "#vista-servicio");
  createModalService(
    ".btn_nuevo_servicio",
    ".modal-overlay_service",
    ".close",
    ".enviar"
  );
  infoCita(".btn-guardar-cita");
});
