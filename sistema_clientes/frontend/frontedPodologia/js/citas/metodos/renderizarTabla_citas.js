import { crearFilaCita } from "./crearFilaCita.js";

export const renderizarTablaCitas = (citas = []) => {
  const $contenidoTabla = document.getElementById("tabla-citas-finalizadas");
  $contenidoTabla.innerHTML = "";
  console.log("🧹 Tabla limpiada, procesando citas...");
  console.log("📄 Iniciando renderizado de citas...");
  console.log("📋 Total de citas a mostrar:", citas.length);

  for (const cita of citas) {
    console.log("📦 Cita recibida:", cita);
    const row = crearFilaCita(cita);
    $contenidoTabla.appendChild(row);
  }

  console.log("✅ Tabla completada");
};
