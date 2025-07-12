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

      // ❌ No se agrega columna de acciones
      // ❌ No se crean botones "Finalizado" ni "Eliminar"

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
