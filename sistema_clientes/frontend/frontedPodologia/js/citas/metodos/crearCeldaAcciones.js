export const crearCeldaAcciones = (cita) => {
  console.log("🛠️ Creando celda de acciones para cita:", cita);
  console.log("📌 Buscando servicio para ID:", cita.servicioId);

  const tdAcciones = document.createElement("td");

  const estado = cita.estadoCita?.toUpperCase(); // Asegúrate de que esté en mayúsculas

  if (estado === "ATENDIDA") {
    const btnFinalizar = document.createElement("button");
    btnFinalizar.textContent = "Finalizado";
    btnFinalizar.classList.add("action-button", "click_finalizar", "green");
    btnFinalizar.dataset.idCita = cita.idCita;

    if (cita?.servicio?.idServicio) {
      console.log("✅ servicioDto existe:", cita.servicio);

      const idServicio = cita.servicio.idServicio;
      btnFinalizar.dataset.servicioId = idServicio;
      btnFinalizar.dataset.servicioNombre = cita.servicio.nombreServicio;

      console.log("📦 Detalles de la cita:", cita.detalles);

      const detalle = Array.isArray(cita.detalles)
        ? cita.detalles.find((d) => String(d.servicioId) === String(idServicio))
        : null;

      if (detalle) {
        console.log("✅ Detalle encontrado:", detalle);
        btnFinalizar.dataset.duracion = detalle.duracionTotal || "";
      } else {
        console.warn(
          "⚠️ No se encontró detalle para el servicio:",
          cita.servicio.idServicio
        );
      }
    } else {
      console.warn(`⚠️ No se encontró servicioDto en la cita ${cita.idCita}`);
      console.log("❓ Estructura actual de cita:", cita);
    }

    tdAcciones.appendChild(btnFinalizar);
  }

  const btnEliminar = document.createElement("button");
  btnEliminar.textContent = "Eliminar";
  btnEliminar.classList.add("action-button", "click_delete", "red");
  btnEliminar.dataset.idCita = cita.idCita;

  tdAcciones.appendChild(btnEliminar);

  return tdAcciones;
};
