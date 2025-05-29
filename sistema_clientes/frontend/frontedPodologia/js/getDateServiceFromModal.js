import { Servicio } from "./clases/servicio.js";
import { enviarCitaApi } from "./conexion/enviarServicioApi.js";

import { sendDataToTablaCite } from "./sendDataToTablaCite.js";

export const datosServicio = [];

export const infoDatosService = async () => {
  const d = document;

  const $formServicio = d.getElementById("form_servicio");

  const $nombreServicio = d.getElementById("modal_nombreServicio").value.trim();
  const $precioService = parseFloat(
    d.getElementById("modal_precio").value.trim()
  );
  const $descripcionService = d
    .getElementById("modal_descripcion")
    .value.trim();
  const $duracionService = parseFloat(
    d.getElementById("modal_duracion").value.trim()
  );

  console.log("Los datos del servicio son:", {
    nombre: $nombreServicio,
    precio: $precioService,
    descripcion: $descripcionService,
    duracion: $duracionService,
  });

  try {
    const servicio = new Servicio(
      $nombreServicio,
      $precioService,
      $descripcionService,
      $duracionService
    );

    datosServicio.push(servicio);
    console.log("cita agregada: ", servicio);

    console.log("➡️ JSON enviado:", JSON.stringify(servicio.toBackendJson()));
    const respuestaService = await enviarCitaApi(servicio.toBackendJson());

    console.log("respuesta: ", respuestaService);

    if (respuestaService.error) {
      alert("❌ Error al enviar al backend: " + respuestaService.mensaje);
    } else {
      alert("✅ servicio enviado exitosamente.");
    }
  } catch (e) {
    alert("❌ Error al crear cliente: " + e.message);
  }
  console.log("lista de servicios: ", datosServicio);
  $formServicio.reset();
  //sendDataToTablaCite();
  sendDataToTablaCite();
};
