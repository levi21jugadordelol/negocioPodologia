import { BASE_URL } from "../config/configuracion.js";

const d = document;

// Obtener las citas programadas
export const getCitePending = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas programadas...");

    // Construir URL con filtros opcionales
    let url = `${BASE_URL}/citas/clientes?estado=PROGRAMADA`;
    if (dni) url += `&dni=${encodeURIComponent(dni)}`;
    else if (nombre) url += `&nombre=${encodeURIComponent(nombre)}`;
    console.log("valor de la url de programada es: ", url);

    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });
    const citas = await response.json();

    console.log(`✅ ${citas.length} citas recibidas`);
    console.log("✅ Respuesta recibida:", citas);

    const $contenidoTablaPendiente = document.getElementById(
      "tabla-citas-programadas"
    );
    $contenidoTablaPendiente.innerHTML = "";
    console.log("🧹 Tabla limpiada, procesando citas...");

    for (const cita of citas) {
      const row = document.createElement("tr");

      row.appendChild(crearCelda(cita.idCita));
      row.appendChild(crearCelda(cita.nombreCliente));
      row.appendChild(crearCelda(cita.servicioDto?.idServicio || "-"));
      row.appendChild(crearCelda(cita.fechaCita));
      row.appendChild(crearCelda(cita.estadoCita));
      row.appendChild(crearCelda(cita.observaciones || "-"));

      const tdAcciones = document.createElement("td");

      const btnFinalizar = document.createElement("button");
      btnFinalizar.textContent = "Finalizado";
      btnFinalizar.classList.add("action-button", "click_finalizar", "green");
      btnFinalizar.dataset.idCita = cita.idCita;

      // ✅ Usa el servicio incluido directamente
      if (cita?.servicioDto?.idServicio) {
        console.log("🧩 Servicio incluido:", cita.servicioDto.idServicio);
        btnFinalizar.dataset.servicioId = cita.servicioDto.idServicio;
        btnFinalizar.dataset.servicioNombre = cita.servicioDto.nombreServicio;
        // ✅ NUEVO: agregar duración si existe
        const detalle = cita.detalles?.find(
          (d) => d.servicioId === cita.servicioDto.idServicio
        );
        btnFinalizar.dataset.duracion = detalle?.duracionTotal || "";
      } else {
        console.warn(`⚠️ No se encontró servicio en la cita ${cita.idCita}`);
      }

      const btnEliminar = document.createElement("button");
      btnEliminar.textContent = "Eliminar";
      btnEliminar.classList.add("action-button", "click_delete", "red");

      tdAcciones.appendChild(btnFinalizar);
      tdAcciones.appendChild(btnEliminar);
      row.appendChild(tdAcciones);

      $contenidoTablaPendiente.appendChild(row);
    }

    console.log("✅ Tabla completada");
  } catch (e) {
    console.error("❌ Error general en getCitePending:", e);
  }
};

const crearCelda = (texto) => {
  const td = document.createElement("td");
  td.textContent = texto;
  return td;
};
