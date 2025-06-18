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
import { cerrandoSession } from "./closeSession/cerrandoSession.js";
import { cargarClientesDelDia } from "./cliente/cargarClienteDelDia.js";
import { evento_excel } from "./cliente/evento_excel.js";
import { eventoEliminarCliente } from "./cliente/eventoEliminarCliente.js";
import { cargarProductosDesdeLocalStorage } from "./localStorage/cargarProductosDesdeLocalStorage.js";
import { recuperarProductosDesdeLocalStorage } from "./producto/recuperarProductosDesdeLocalStorage.js";
import { eventoEditDeleteProducto } from "./producto/evento_producto/eventoEditDeleteProducto.js";
import { eventoEditDeleteServicio } from "./servicios/evento_servicio/eventoEditDeleteServicio.js";
import { recuperarServiciosDesdeLocalStorage } from "./servicios/metodos/recuperarServiciosDesdeLocalStorage.js";
import { cargarServiciosDesdeLocalStorage } from "./localStorage/cargarServiciosDesdeLocalStorage.js";

document.addEventListener("DOMContentLoaded", async () => {
  console.log("🚀 DOM listo, inicializando app...");

  localStorage.removeItem("citasStorage");
  const clientesDelDia = await cargarClientesDelDia();
  console.log("📅 Clientes cargados al iniciar:", clientesDelDia); // 👈
  cargarClientesDesdeLocalStorage(); // 👈 cargar desde localStorage
  cargarCitasDesdeLocalStorage();
  cargarProductosDesdeLocalStorage();
  console.log("📦 datosCliente actuales:", datosCliente); // 👈 ¿está vacío?
  //enviandoDatos(datosCliente);
  const datosProduct = recuperarProductosDesdeLocalStorage();
  //recuperarServiciosDesdeLocalStorage();
  cargarServiciosDesdeLocalStorage();
  console.log("los productos en el domContenLoaded: ", datosProduct);
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
  openTablePending(
    ".btn_verPendientes",
    "#vista-clientes-pendientes",
    ".btn_verFinalizadas",
    "#vista-clientes-finalizadas",
    ".btn_verCanceladas",
    "#vista-clientes-cancelados"
  );
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
  mostrarListaClientes(
    ".btn_listaCliente",
    "#vista-clientes-totales",
    ".click_delete_cliente_total"
  );
  evento_excel(".btn_enviarExcel");
  eventoEliminarCliente(".click_delete_cliente");
  eventoEditDeleteProducto(
    ".click_editar_producto",
    ".click_delete_producto",
    ".close",
    ".btn_edit_producto"
  );
  eventoEditDeleteServicio(
    ".click_editar_servicio",
    ".click_delete_servicio",
    ".btn_enviarEdit_servicio",
    ".close"
  );
  cerrandoSession("#btn_cerrarSession");
});

tableCita("#btn-citas", "#vista-citas");
