export const crearCeldaAcciones = (cita) => {
  const tdAcciones = document.createElement("td");

  const estado = cita.estadoCita?.toUpperCase(); // Asegúrate de que esté en mayúsculas

  if (estado === "ATENDIDA") {
    const btnFinalizar = document.createElement("button");
    btnFinalizar.textContent = "Finalizado";
    btnFinalizar.classList.add("action-button", "click_finalizar", "green");
    btnFinalizar.dataset.idCita = cita.idCita;

    if (cita?.servicioDto?.idServicio) {
      btnFinalizar.dataset.servicioId = cita.servicioDto.idServicio;
      btnFinalizar.dataset.servicioNombre = cita.servicioDto.nombreServicio;

      const detalle = cita.detalles?.find(
        (d) => d.servicioId === cita.servicioDto.idServicio
      );
      btnFinalizar.dataset.duracion = detalle?.duracionTotal || "";
    } else {
      console.warn(`⚠️ No se encontró servicio en la cita ${cita.idCita}`);
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
