import { datosCliente } from "./obteniendoDatos.js";

export const enviandoDatos = () => {
  const d = document;
  const tableDatesUser = d.getElementById("tabla-clientes");

  tableDatesUser.innerHTML = "";

  datosCliente.forEach((cliente) => {
    console.log(
      "Cliente id:",
      cliente.id,
      "Cliente idCliente:",
      cliente.idCliente
    );
    const fila = d.createElement("tr");

    // Aquí asignamos el ID del cliente al data-id de la fila
    // fila.dataset.id = cliente.id;
    fila.dataset.id = cliente.idCliente || cliente.id || "";

    fila.innerHTML = `
      <td>${cliente.nombre}</td>
      <td>${cliente.apellido}</td>
      <td>${cliente.correo}</td>
      <td>${cliente.celular}</td>
      <td>${cliente.dni}</td>
    `;

    const celdaAcciones = d.createElement("td");

    // Crear botones
    const botonEditar = d.createElement("button");
    const buttonDeleteClient = d.createElement("button");
    const buttonCreateCiteToClient = d.createElement("button");

    // Texto
    botonEditar.textContent = "Editar";
    buttonDeleteClient.textContent = "Eliminar";
    buttonCreateCiteToClient.textContent = "Crear Cita";

    // Clases comunes
    [botonEditar, buttonDeleteClient, buttonCreateCiteToClient].forEach(
      (btn) => {
        btn.classList.add("action-button");
      }
    );

    // Clases específicas
    botonEditar.classList.add("click_editar", "green");
    buttonDeleteClient.classList.add("click_delete", "red");
    buttonCreateCiteToClient.classList.add("click_createCite", "black");

    // Agregar botones a celda
    celdaAcciones.appendChild(botonEditar);
    celdaAcciones.appendChild(buttonDeleteClient);
    celdaAcciones.appendChild(buttonCreateCiteToClient);
    fila.appendChild(celdaAcciones);

    tableDatesUser.appendChild(fila);
  });
};
