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

    // Acciones
    /* const tdAcciones = d.createElement("td");

    const btnGuardar = d.createElement("button");
    btnGuardar.textContent = "Guardar"; // ✅ Corregido nombre
    btnGuardar.className = "btn btn-success btn-sm me-1";
    btnGuardar.classList.add("btn-guardar-cita");

    const btnGuardarEdit = document.createElement("button");
    btnGuardarEdit.textContent = "Guardar cambios";
    btnGuardarEdit.className =
      "btn btn-primary btn-sm me-1 btn-guardar-edit d-none";

    const btnEditar = d.createElement("button");
    btnEditar.textContent = "Editar";
    btnEditar.className = "btn btn-warning btn-sm me-1";
    btnEditar.classList.add("btn-editar-cita");

    const btnEliminar = d.createElement("button");
    btnEliminar.textContent = "Eliminar";
    btnEliminar.className = "btn btn-danger btn-sm";
    btnEliminar.classList.add("btn-eliminar-cita");

    // Agrega los botones a la celda

    tdAcciones.appendChild(btnGuardar);
    tdAcciones.appendChild(btnGuardarEdit);
    tdAcciones.appendChild(btnEditar);
    tdAcciones.appendChild(btnEliminar);

    // Añade la celda de acciones a la fila
    fila.appendChild(tdAcciones); */

    // Agregar fila a tabla
    $tablaCitasDelDia.appendChild(fila);
  });
};
