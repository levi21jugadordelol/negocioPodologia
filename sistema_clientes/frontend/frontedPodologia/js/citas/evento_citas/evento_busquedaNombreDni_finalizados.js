import { getCiteFinished } from "../../cliente/cliente_finalizado/getCiteFinished.js";

const d = document;

export const evento_busquedaNombreDni_finalizado = (
  btn_dni_finalizado,
  btn_nombre_finalizado
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btn_dni_finalizado)) {
      const dni = document.getElementById("input-dni-finalizado").value.trim();
      //getCitePending({ dni });

      getCiteFinished({ dni });
    }
    if (e.target.matches(btn_nombre_finalizado)) {
      const nombre = document
        .getElementById("input-nombre-finalizado")
        .value.trim();
      //getCitePending({ nombre });
      getCiteFinished({ nombre });
    }
  });
};
