/*
document.addEventListener("DOMContentLoaded", () => {
  getCitePending(); // carga inicial

  document.getElementById("btn-buscar-dni").addEventListener("click", () => {
    const dni = document.getElementById("input-dni").value.trim();
    getCitePending({ dni });
  });

  document.getElementById("btn-buscar-nombre").addEventListener("click", () => {
    const nombre = document.getElementById("input-nombre").value.trim();
    getCitePending({ nombre });
  });
}); */

import { getCitePending } from "../cliente_pendientes/getCitePending.js";

const d = document;
export const event_busqueda_filtro = (btn_dni, btn_nombre) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btn_dni)) {
      const dni = document.getElementById("input-dni").value.trim();
      getCitePending({ dni });
    }
    if (e.target.matches(btn_nombre)) {
      const nombre = document.getElementById("input-nombre").value.trim();
      getCitePending({ nombre });
    }
  });
};
