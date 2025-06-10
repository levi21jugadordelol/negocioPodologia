import crearModalNuevoCliente from "./crearModalNuevoCliente.js";
import { createModalService } from "./makeModalNewService.js";

import { sendClientToCita } from "./sendInfoToTableCite.js";
import { tableCita } from "./windowForCite.js";
import { tableClient } from "./windowForClient.js";
import { invocateDivServicio } from "./windowToServicio.js";
import { infoCita } from "./saveDate.js";
import { openWindowProduct } from "./openWindowProduct.js";
import { openModalProducto } from "./openModalProducto.js";
import { openTablePending } from "./cliente_pendientes/openTablePending.js";
import { openModalDetailsService } from "./cliente_pendientes/openModalDetailsService.js";
import { cargarClientesDesdeLocalStorage } from "./localStorage/cargarClientesDesdeLocalStorage.js";
import { enviandoDatos } from "./enviandoDatos.js";
import { cargarCitasDesdeLocalStorage } from "./localStorage/cargarCitasDesdeLocalStorage.js";
import { datosCliente } from "./obteniendoDatos.js";
import { event_busqueda_filtro } from "./citas/event_busqueda_filtro.js";
import { edit_cliente } from "./cliente/edit_cliente.js";
import { evento_edit } from "./citas/evento_citas/evento_edit.js";
import { event_formulario } from "./usuario/evento_usuario/event_form.js";
import { mostrarListaClientes } from "./cliente/mostrarListaCliente.js";

document.addEventListener("DOMContentLoaded", () => {
  localStorage.removeItem("citasStorage");
  cargarClientesDesdeLocalStorage(); // 👈 cargar desde localStorage
  cargarCitasDesdeLocalStorage();
  enviandoDatos(datosCliente);
  crearModalNuevoCliente(
    ".btn_nuevo_cliente",
    ".modal-overlay",
    ".close",
    ".button_enviar_cliente"
  );
  tableCita("#btn-citas", "#vista-citas");
  sendClientToCita(".click_createCite", "#vista-citas", "#vista-clientes"); //click_createCite, button creado en sendInfoToTable y vista-citas, id dentro del html para el div donde esta citas
  tableClient("#btn-clientes", "#vista-clientes");
  invocateDivServicio("#btn-servicios", "#vista-servicio");
  createModalService(
    ".btn_nuevo_servicio",
    ".modal-overlay_service",
    ".close",
    ".btn_enviar_servicio"
  );
  infoCita(".btn-guardar-cita");
  openWindowProduct("#btn-productos", "#vista-productos");
  openModalProducto(
    ".btn_nuevo_producto",
    "#modal_producto",
    ".close",
    ".btn_enviar_producto"
  );
  openTablePending(".btn_verPendientes", "#vista-clientes-pendientes");
  openModalDetailsService(
    ".click_finalizar",
    "#modal_detalle",
    ".close",
    ".enviar"
  );
  event_busqueda_filtro("#btn-buscar-dni", "#btn-buscar-nombre");
  edit_cliente(
    ".click_editar",
    ".modal-overlay_edit",
    ".close",
    ".btn_enviar_edit_cliente"
  );
  evento_edit(".btn-editar-cita", ".btn-guardar-cita");
  mostrarListaClientes(".btn_listaCliente");
});

tableCita("#btn-citas", "#vista-citas");
