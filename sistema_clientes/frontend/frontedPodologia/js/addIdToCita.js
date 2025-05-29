import {
  llenarComboEstadoCita,
  llenarComboTipoCita,
} from "./conexion/fillComboFromBackend.js";
import { llenarComboServicio } from "./conexion/fillComboServiceFromBackend.js";

export const addIdToTable = async (idCliente) => {
  const d = document;
  console.log("ID recibido:", idCliente);

  const $tableCita = d.querySelector("#tabla-citas tbody");

  // 🔒 Verificar si el cliente ya tiene una cita en la tabla
  //esto hace que tenga varios id de cliente y que no se pisen
  const idYaExiste = [...$tableCita.querySelectorAll("tr")].some(
    (fila) => fila.children[1].textContent === idCliente
  );

  if (idYaExiste) {
    alert("Este cliente ya tiene una cita pendiente en la tabla.");
    return;
  }

  console.log("📋 Agregando fila para cliente:", idCliente);

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
  tdServicio.appendChild(selectServicio);
  fila.appendChild(tdServicio);
  console.log("🛠️ Combo de Servicio creado (vacío aún).");

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

  // Acciones (Guardar, Editar, Eliminar)
  const tdAcciones = d.createElement("td");

  const btnGuardar = d.createElement("button");
  btnGuardar.textContent = "Guardar";
  btnGuardar.className = "btn btn-success btn-sm me-1";
  btnGuardar.classList.add("btn-guardar-cita");
  // Aquí puedes agregarle evento click si quieres

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
  tdAcciones.appendChild(btnEditar);
  tdAcciones.appendChild(btnEliminar);

  // Añade la celda de acciones a la fila
  fila.appendChild(tdAcciones);

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
  await llenarComboServicio(selectServicio);
};
