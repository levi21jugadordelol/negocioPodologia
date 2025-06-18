import { datosServicio } from "./getDateServiceFromModal.js";

export const sendDataToTablaCite = () => {
  const d = document;
  const tableDataCite = d.getElementById("tabla-servicio");

  tableDataCite.innerHTML = "";

  datosServicio.forEach((servicio) => {
    const fila = d.createElement("tr");

    /*fila.innerHTML = `
       <td>${servicio.$nombreServicio}</td>
       <td>${servicio.$nombreServicio}</td>
       <td>${servicio.$nombreServicio}</td>
       <td>${servicio.$nombreServicio}</td>
    `;*/

    fila.dataset.id = servicio.id || servicio.idServicio;

    fila.innerHTML = `
  <td>${servicio.nombre}</td>
  <td>${servicio.precio}</td>
  <td>${servicio.descripcion}</td>
  <td>${servicio.duracion}</td>
`;
    const celdaAcciones = d.createElement("td");

    // Crear botones
    const botonEditar = d.createElement("button");
    const buttonDeleteServicio = d.createElement("button");

    // Texto
    botonEditar.textContent = "Editar";
    buttonDeleteServicio.textContent = "Eliminar";

    // Clases comunes
    [botonEditar, buttonDeleteServicio].forEach((btn) => {
      btn.classList.add("action-button");
    });

    // Clases específicas
    botonEditar.classList.add("click_editar_servicio", "green");
    buttonDeleteServicio.classList.add("click_delete_servicio", "red");

    // Agregar botones a celda
    celdaAcciones.appendChild(botonEditar);
    celdaAcciones.appendChild(buttonDeleteServicio);
    fila.appendChild(celdaAcciones);

    tableDataCite.appendChild(fila);
  });
};
