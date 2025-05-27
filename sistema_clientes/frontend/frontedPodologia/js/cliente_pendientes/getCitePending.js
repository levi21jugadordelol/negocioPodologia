import { BASE_URL } from "../config/configuracion.js";

const d = document;

export const getCitePending = async () => {
  try {
    const response = await fetch(
      `${BASE_URL}/citas/clientes?estado=PROGRAMADA`
    );
    const estado = await response.json();
    console.log(estado);

    const $contenidoTablaPendiente = d.getElementById(
      "tabla-citas-programadas"
    );

    $contenidoTablaPendiente.innerHTML = "";

    estado.forEach((cita) => {
      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${cita.idCita}</td>
       <td>${cita.nombreCliente}</td>
        <td>${cita.tipoCita}</td>
        <td>${cita.fechaCita}</td>
        <td>${cita.estadoCita}</td>
        <td>${cita.observaciones}</td>
      `;

      $contenidoTablaPendiente.appendChild(row);

      //se crea otra columna
      const celdaAcciones = d.createElement("td");

      //se crea los botones de cumplido y eliminar
      const botonCumplid = d.createElement("button");
      const buttonDelete = d.createElement("button");

      // Texto
      botonCumplid.textContent = "terminado";
      buttonDelete.textContent = "Eliminar";

      // Clases comunes
      [botonCumplid, buttonDelete].forEach((btn) => {
        btn.classList.add("action-button");
      });

      // Clases específicas
      botonCumplid.classList.add("click_editar", "green");
      buttonDelete.classList.add("click_delete", "red");

      // Agregar botones a celda
      celdaAcciones.appendChild(botonCumplid);
      celdaAcciones.appendChild(buttonDelete);

      row.appendChild(celdaAcciones);

      $contenidoTablaPendiente.appendChild(row);
    });
  } catch (e) {
    console.error("Error al cargar estados de cita:", e);
  }
};
