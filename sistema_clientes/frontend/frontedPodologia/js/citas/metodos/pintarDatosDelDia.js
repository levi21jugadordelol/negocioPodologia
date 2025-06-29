const d = document;

export const pintarDatosDelDia = (citas = []) => {
  const d = document;
  const $tablaCitasDelDia = d.getElementById("tbody-citas-xDia");

  $tablaCitasDelDia.innerHTML = ""; // Limpiar antes de pintar

  citas.forEach((cita, i) => {
    const fila = d.createElement("tr");
    fila.dataset.id = cita.idCita || cita.id || "";

    // Fecha (como texto plano, no editable)
    const tdFecha = d.createElement("td");
    tdFecha.textContent = cita.fechaCita || cita.fecha || "";
    fila.appendChild(tdFecha);

    // Cliente
    const tdCliente = d.createElement("td");
    tdCliente.dataset.idcliente = cita.cliente?.id || cita.clienteId || "";
    tdCliente.textContent = cita.cliente?.nombre || cita.nombreCliente || "";
    fila.appendChild(tdCliente);

    // Servicio
    const tdServicio = d.createElement("td");
    tdServicio.textContent = cita.servicio?.nombreServicio || "";
    fila.appendChild(tdServicio);

    // Estado
    const tdEstado = d.createElement("td");
    tdEstado.textContent = cita.estadoCita || cita.estado || "";
    fila.appendChild(tdEstado);

    // Observaciones
    const tdObs = d.createElement("td");
    tdObs.textContent = cita.observaciones || "";
    fila.appendChild(tdObs);

    // Agregar fila a tabla
    $tablaCitasDelDia.appendChild(fila);
  });
};
