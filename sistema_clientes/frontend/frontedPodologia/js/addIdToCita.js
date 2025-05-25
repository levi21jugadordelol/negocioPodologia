import {
  llenarComboEstadoCita,
  llenarComboTipoCita,
} from "./conexion/fillComboFromBackend.js";

export const addIdToTable = async (idCliente) => {
  const d = document;
  console.log("ID recibido:", idCliente);
  const $tableCita = d.querySelector("#tabla-citas tbody");

  $tableCita.innerHTML = "";

  // Crear nueva fila
  const fila = d.createElement("tr");

  // Fecha
  const tdFecha = d.createElement("td");
  const inputFecha = d.createElement("input");
  inputFecha.type = "text"; // flatpickr necesita text
  inputFecha.className = "form-control fecha-cita";
  inputFecha.setAttribute("placeholder", "Selecciona fecha y hora");
  tdFecha.appendChild(inputFecha);
  fila.appendChild(tdFecha);

  // 👤 Cliente (oculto, pero se enviará)
  const tdClienteVisible = d.createElement("td");
  tdClienteVisible.textContent = idCliente; // Muestra el id en la tabla
  fila.appendChild(tdClienteVisible);

  // Servicio
  const tdServicio = d.createElement("td");
  const selectServicio = d.createElement("select");
  selectServicio.className = "form-select";
  selectServicio.innerHTML = `<option disabled selected>Seleccione servicio</option>`; // luego lo llenarás
  tdServicio.appendChild(selectServicio);
  fila.appendChild(tdServicio);

  // Tipo de cita (llenado dinámico)
  const tdTipo = d.createElement("td");
  const selectTipo = d.createElement("select");
  selectTipo.className = "form-select";
  tdTipo.appendChild(selectTipo);
  fila.appendChild(tdTipo);

  // Estado (llenado dinámico)
  const tdEstado = d.createElement("td");
  const selectEstado = d.createElement("select");
  selectEstado.className = "form-select";
  tdEstado.appendChild(selectEstado);
  fila.appendChild(tdEstado);

  // Observaciones
  const tdObs = d.createElement("td");
  const inputObs = d.createElement("input");
  inputObs.type = "text";
  inputObs.className = "form-control";
  inputObs.setAttribute("placeholder", "Observaciones");
  tdObs.appendChild(inputObs);
  fila.appendChild(tdObs);

  // Agregar fila a tabla
  $tableCita.appendChild(fila);

  // Flatpickr en inputFecha
  flatpickr(inputFecha, {
    enableTime: true,
    dateFormat: "Y-m-d\\TH:i",
  });

  // Llenar combos dinámicos
  await llenarComboEstadoCita(selectEstado);
  await llenarComboTipoCita(selectTipo);
};
