import { getCiteCancelado } from "../../cliente/cliente_cancelado/getCiteCancelado.js";

const d = document;

export const eventoBusquedaNombreDni_cancelados = (
  btn_dni_cancelados,
  btn_nombre_cancelados
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btn_dni_cancelados)) {
      const dni = d.getElementById("input-dni-cancelados").value.trim();
      getCiteCancelado({ dni });
    }
    if (e.target.matches(btn_nombre_cancelados)) {
      const nombre = d.getElementById("input-nombre-cancelados").value.trim();
      getCiteCancelado({ nombre });
    }
  });
};
