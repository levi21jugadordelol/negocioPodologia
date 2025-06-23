import { crearCeldaAcciones } from "./crearCeldaAcciones.js";
import { crearCelda } from "./crearCelda.js";

export const crearFilaCita = (cita) => {
  // Obtener datos correctamente según lo que llega del backend
  const idCita = cita.id;
  const servicioId = cita.servicio?.idServicio ?? "-";
  const nombreServicio = cita.servicio?.nombreServicio ?? "-";

  console.log("🧱 Creando fila para la cita:");
  console.log("🆔 ID:", idCita);
  console.log("👤 Cliente:", cita.nombreCliente);
  console.log("🧩 Servicio ID:", servicioId);
  console.log("📅 Fecha:", cita.fechaCita);
  console.log("📌 Estado:", cita.estadoCita);
  console.log("📝 Observaciones:", cita.observaciones);

  const row = document.createElement("tr");

  // Usamos los valores correctos
  row.appendChild(crearCelda(idCita));
  row.appendChild(crearCelda(cita.nombreCliente));
  row.appendChild(crearCelda(nombreServicio)); // más útil que solo ID
  row.appendChild(crearCelda(cita.fechaCita));
  row.appendChild(crearCelda(cita.estadoCita));
  row.appendChild(crearCelda(cita.observaciones || "-"));

  row.appendChild(crearCeldaAcciones(cita)); // usa el objeto completo

  console.log("✅ Fila creada para la cita:", idCita);

  return row;
};
