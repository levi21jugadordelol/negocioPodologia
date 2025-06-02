//import { datosCliente } from "./obteniendoDatos.js";

export const enviandoDatos = (clientes = []) => {
  const d = document;
  const tableDatesUser = d.getElementById("tabla-clientes");

  tableDatesUser.innerHTML = "";

  clientes.forEach((cliente) => {
    const fila = d.createElement("tr");

    // Asignamos el ID del cliente al data-id de la fila
    fila.dataset.id = cliente.idCliente || cliente.id || "";

    fila.innerHTML = `
      <td>${cliente.nombre}</td>
      <td>${cliente.apellido}</td>
      <td>${cliente.correo}</td>
      <td>${cliente.celular}</td>
      <td>${cliente.dni}</td>
    `;

    const celdaAcciones = d.createElement("td");

    const botonEditar = d.createElement("button");
    const buttonDeleteClient = d.createElement("button");
    const buttonCreateCiteToClient = d.createElement("button");

    botonEditar.textContent = "Editar";
    buttonDeleteClient.textContent = "Eliminar";
    buttonCreateCiteToClient.textContent = "Crear Cita";

    [botonEditar, buttonDeleteClient, buttonCreateCiteToClient].forEach(
      (btn) => {
        btn.classList.add("action-button");
      }
    );

    botonEditar.classList.add("click_editar", "green");
    buttonDeleteClient.classList.add("click_delete", "red");
    buttonCreateCiteToClient.classList.add("click_createCite", "black");

    celdaAcciones.appendChild(botonEditar);
    celdaAcciones.appendChild(buttonDeleteClient);
    celdaAcciones.appendChild(buttonCreateCiteToClient);
    fila.appendChild(celdaAcciones);

    tableDatesUser.appendChild(fila);
  });
};
