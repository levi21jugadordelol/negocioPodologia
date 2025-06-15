import { crearCeldaAcciones } from "./crearCeldaAcciones.js";
import { crearCelda } from "./crearCelda.js";

export const crearFilaCita = (cita) => {
  const row = document.createElement("tr");

  row.appendChild(crearCelda(cita.idCita));
  row.appendChild(crearCelda(cita.nombreCliente));
  row.appendChild(crearCelda(cita.servicioDto?.idServicio || "-"));
  row.appendChild(crearCelda(cita.fechaCita));
  row.appendChild(crearCelda(cita.estadoCita));
  row.appendChild(crearCelda(cita.observaciones || "-"));

  row.appendChild(crearCeldaAcciones(cita));

  return row;
};
