const d = document;

import { crearFilaCita } from "./crearFilaCita.js";

export const renderizarTablaCitaCanceladas = (citas = []) => {
  const $contenidoTabla = document.getElementById("tabla-citas-cancelados");
  $contenidoTabla.innerHTML = "";
  console.log("🧹 Tabla limpiada, procesando citas...");

  for (const cita of citas) {
    const row = crearFilaCita(cita);
    $contenidoTabla.appendChild(row);
  }

  console.log("✅ Tabla completada");
};
