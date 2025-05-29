import { BASE_URL } from "../config/configuracion.js";

const d = document;

export const getCitePending = async () => {
  try {
    console.log("🔄 Iniciando solicitud de citas programadas...");
    const response = await fetch(
      `${BASE_URL}/citas/clientes?estado=PROGRAMADA`
    );
    const estado = await response.json();
    console.log("✅ Respuesta recibida:", estado);

    const $contenidoTablaPendiente = document.getElementById(
      "tabla-citas-programadas"
    );
    $contenidoTablaPendiente.innerHTML = "";
    console.log("🧹 Tabla limpiada, procesando citas...");

    // ✅ Cambiar de forEach a for...of
    for (const cita of estado) {
      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${cita.idCita}</td>
        <td>${cita.nombreCliente}</td>
        <td>${cita.tipoCita}</td>
        <td>${cita.fechaCita}</td>
        <td>${cita.estadoCita}</td>
        <td>${cita.observaciones}</td>
      `;

      const celdaAcciones = document.createElement("td");
      const botonCumplid = document.createElement("button");
      const buttonDelete = document.createElement("button");

      botonCumplid.textContent = "finalizado";
      buttonDelete.textContent = "Eliminar";

      botonCumplid.dataset.idCita = cita.idCita;
      console.log(`🆔 ID de cita asignado: ${cita.idCita}`);

      let servicioId;
      if (cita.servicio) {
        servicioId = cita.servicio.idServicio;
        console.log("🧩 Servicio cargado en dataset:", { id: servicioId });
      } else {
        try {
          const servicioRes = await fetch(
            `${BASE_URL}/servicio/buscarPorIdCita/${cita.idCita}`
          );
          if (servicioRes.ok) {
            const servicio = await servicioRes.json();
            servicioId = servicio.idServicio;
            console.log(
              "🧩 Servicio cargado manualmente desde endpoint:",
              servicio
            );
          } else {
            console.warn(`⚠️ No se encontró servicio para cita ${cita.idCita}`);
          }
        } catch (err) {
          console.error(
            `❌ Error al buscar servicio para cita ${cita.idCita}:`,
            err
          );
        }
      }

      if (servicioId) {
        botonCumplid.dataset.servicioId = servicioId;
      }

      [botonCumplid, buttonDelete].forEach((btn) => {
        btn.classList.add("action-button");
      });

      botonCumplid.classList.add("click_finalizar", "green");
      buttonDelete.classList.add("click_delete", "red");

      celdaAcciones.appendChild(botonCumplid);
      celdaAcciones.appendChild(buttonDelete);

      row.appendChild(celdaAcciones);
      $contenidoTablaPendiente.appendChild(row);

      console.log("✅ Botones agregados a la fila.");
    }
  } catch (e) {
    console.error("❌ Error al cargar estados de cita:", e);
  }
};
