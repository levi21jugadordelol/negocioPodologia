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
