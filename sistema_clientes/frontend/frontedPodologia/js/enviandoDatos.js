import { datosCliente } from "./obteniendoDatos.js";

export const enviandoDatos = () => {
  const d = document;
  const tableDatesUser = d.getElementById("tabla-clientes");

  tableDatesUser.innerHTML = "";

  datosCliente.forEach((cliente) => {
    const fila = d.createElement("tr");

    fila.innerHTML = `
      <td>${cliente.nombre}</td>
      <td>${cliente.apellido}</td>
      <td>${cliente.correo}</td>
      <td>${cliente.celular}</td>
      <td>${cliente.dni}</td>
    `;

    const celdaAcciones = d.createElement("td");
    const botonEditar = d.createElement("button");

    botonEditar.classList.add("click_editar");
    botonEditar.textContent = "Editar";
    botonEditar.style.backgroundColor = "green";
    botonEditar.style.color = "white";

    celdaAcciones.appendChild(botonEditar);
    fila.appendChild(celdaAcciones);

    tableDatesUser.appendChild(fila);
  });
};
