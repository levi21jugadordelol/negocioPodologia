const d = document;

export const imprimirCitasFinalizadas = (citas = []) => {
  const $contenidoTablaCitasFinalizadas = d.getElementById(
    "tabla-citas-finalizadas"
  );
  $contenidoTablaCitasFinalizadas.innerHTML = "";
  console.log("🧹 Tabla limpiada, procesando citas...");
  try {
    for (const cita of citas) {
      const row = document.createElement("tr");

      const idCita = cita.id;
      const nombreCliente = cita.nombreCliente;
      const servicioId = cita.servicio?.idServicio ?? "-";
      const nombreServicio = cita.servicio?.nombreServicio ?? "-";
      const fechaCita = cita.fechaCita;
      const estadoCita = cita.estadoCita;
      const observaciones = cita.observaciones || "-";

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

      tdAcciones.appendChild(btnFinalizar);
      tdAcciones.appendChild(btnEliminar);
      row.appendChild(tdAcciones);

      $contenidoTablaCitasFinalizadas.appendChild(row);
    }

    console.log("✅ Tabla completada");
  } catch (error) {
    console.error("❌ Error general en imprimirCitasFinalizadas:", error);
  }
};

const crearCelda = (texto) => {
  const td = document.createElement("td");
  td.textContent = texto;
  return td;
};
