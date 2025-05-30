import { BASE_URL } from "../config/configuracion.js";

const d = document;

// Obtener las citas programadas
export const getCitePending = async () => {
  try {
    console.log("🔄 Solicitando citas programadas...");

    const response = await fetch(
      `${BASE_URL}/citas/clientes?estado=PROGRAMADA`
    );
    const citas = await response.json();

    console.log(`✅ ${citas.length} citas recibidas`);
    console.log("✅ Respuesta recibida:", citas);

    const $contenidoTablaPendiente = document.getElementById(
      "tabla-citas-programadas"
    );
    $contenidoTablaPendiente.innerHTML = "";
    console.log("🧹 Tabla limpiada, procesando citas...");

    for (const cita of citas) {
      const row = document.createElement("tr");

      row.appendChild(crearCelda(cita.idCita));
      row.appendChild(crearCelda(cita.nombreCliente));
      row.appendChild(crearCelda(cita.tipoCita));
      row.appendChild(crearCelda(cita.fechaCita));
      row.appendChild(crearCelda(cita.estadoCita));
      row.appendChild(crearCelda(cita.observaciones || "-"));

      const tdAcciones = document.createElement("td");

      const btnFinalizar = document.createElement("button");
      btnFinalizar.textContent = "Finalizado";
      btnFinalizar.classList.add("action-button", "click_finalizar", "green");
      btnFinalizar.dataset.idCita = cita.idCita;

      // ✅ Usa el servicio incluido directamente
      if (cita?.servicioDto?.idServicio) {
        console.log("🧩 Servicio incluido:", cita.servicioDto.idServicio);
        btnFinalizar.dataset.servicioId = cita.servicioDto.idServicio;
        btnFinalizar.dataset.servicioNombre = cita.servicioDto.nombreServicio;
      } else {
        console.warn(`⚠️ No se encontró servicio en la cita ${cita.idCita}`);
      }

      const btnEliminar = document.createElement("button");
      btnEliminar.textContent = "Eliminar";
      btnEliminar.classList.add("action-button", "click_delete", "red");

      tdAcciones.appendChild(btnFinalizar);
      tdAcciones.appendChild(btnEliminar);
      row.appendChild(tdAcciones);

      $contenidoTablaPendiente.appendChild(row);
    }

    console.log("✅ Tabla completada");
  } catch (e) {
    console.error("❌ Error general en getCitePending:", e);
  }
};

const crearCelda = (texto) => {
  const td = document.createElement("td");
  td.textContent = texto;
  return td;
};

/*export const getCitePending = async () => {
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
};*/
