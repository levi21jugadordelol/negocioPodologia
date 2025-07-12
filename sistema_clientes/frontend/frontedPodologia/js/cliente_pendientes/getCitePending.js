import { BASE_URL } from "../config/configuracion.js";

const d = document;

// Obtener las citas programadas
export const getCitePending = async ({ dni = "", nombre = "" } = {}) => {
  try {
    console.log("🔄 Solicitando citas programadas...");

    let url = `${BASE_URL}/citas/clientes?estado=PROGRAMADA`;
    if (dni) url += `&dni=${encodeURIComponent(dni)}`;
    else if (nombre) url += `&nombre=${encodeURIComponent(nombre)}`;
    console.log("🧭 URL: ", url);

    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`⛔ HTTP ${response.status}: ${response.statusText}`);
    }

    const citas = await response.json();

    console.log(`✅ [getCitePending] ${citas.length} citas recibidas`);
    console.table(citas);

    const $contenidoTablaPendiente = document.getElementById(
      "tabla-citas-programadas"
    );
    $contenidoTablaPendiente.innerHTML = "";
    console.log("🧹 Tabla limpiada, procesando citas...");

    for (const cita of citas) {
      const row = document.createElement("tr");

      const idCita = cita.id;
      const nombreCliente = cita.nombreCliente;
      const servicioId = cita.servicio?.idServicio ?? "-";
      const nombreServicio = cita.servicio?.nombreServicio ?? "-";
      const fechaCita = cita.fechaCita;
      const estadoCita = cita.estadoCita;
      const observaciones = cita.observaciones || "-";

      row.dataset.id = idCita;

      console.log(`📄 Procesando cita ID ${idCita}`, {
        nombreCliente,
        nombreServicio,
        fechaCita,
        estadoCita,
        observaciones,
      });

      row.appendChild(crearCelda(idCita));
      row.appendChild(crearCelda(nombreCliente));
      row.appendChild(crearCelda(nombreServicio));
      row.appendChild(crearCelda(fechaCita));
      row.appendChild(crearCelda(estadoCita));
      row.appendChild(crearCelda(observaciones));

      const tdAcciones = document.createElement("td");

      // tdAcciones.appendChild(btnEditar);

      const btnFinalizar = document.createElement("button");
      btnFinalizar.textContent = "Finalizado";
      btnFinalizar.classList.add("action-button", "click_finalizar", "green");
      btnFinalizar.dataset.idCita = idCita;

      const detalle = cita.detalles?.[0]; // primero si existe

      if (detalle?.servicioId) {
        console.log("🧩 Servicio desde detalle:", detalle.servicioId);
        btnFinalizar.dataset.servicioId = detalle.servicioId;
        btnFinalizar.dataset.servicioNombre = detalle.nombreServicio || "-";
        btnFinalizar.dataset.duracion = detalle.duracionTotal || "";
      } else if (cita.servicio) {
        console.log("🧩 Servicio desde cita:", servicioId);
        btnFinalizar.dataset.servicioId = servicioId;
        btnFinalizar.dataset.servicioNombre = nombreServicio;
        btnFinalizar.dataset.duracion = cita.servicio.duracionServicio || "";
      } else {
        console.warn(`⚠️ No se encontró servicio en cita ${idCita}`);
      }

      const btnEliminar = document.createElement("button");
      btnEliminar.textContent = "Eliminar";
      btnEliminar.classList.add("action-button", "click_delete", "red");
      btnEliminar.dataset.idCita = idCita;

      tdAcciones.appendChild(btnFinalizar);
      tdAcciones.appendChild(btnEliminar);
      //  tdAcciones.appendChild(btnEditar);
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
