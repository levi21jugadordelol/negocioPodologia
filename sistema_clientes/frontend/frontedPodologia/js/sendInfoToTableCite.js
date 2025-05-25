import { addIdToTable } from "./addIdToCita.js";

const d = document;

export const sendClientToCita = (
  btn_windowCite,
  window_cite,
  window_client
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btn_windowCite)) {
      const $windowCite = d.querySelector(window_cite); // 💡 corregido
      const $windowClient = d.querySelector(window_client);
      if ($windowCite) {
        $windowCite.classList.add("active");
        $windowClient.classList.remove("active");
      } // Obtener el ID del cliente desde el formulario
      const formulario = d.getElementById("form_cliente");

      //El operador ?. verifica si el valor anterior no es null ni undefined antes de intentar acceder a su propiedad. Si lo es, devuelve undefined en lugar de lanzar un error.
      //Si no actualizas el data-id desde JS, siempre estará vacío y por eso tu función no recibe el ID.
      const idCliente = formulario?.dataset.id;
      if (idCliente) {
        console.log(`✔️ Enviando ID de cliente a addIdToTable: ${idCliente}`);
        addIdToTable(idCliente);
      } else {
        console.warn(
          "❌ No hay ID de cliente disponible para enviar a addIdToTable."
        );
        alert("Primero debes registrar o seleccionar un cliente válido.");
      }
    }
  });
};
