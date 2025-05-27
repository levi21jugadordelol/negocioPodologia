import { addIdToTable } from "./addIdToCita.js";

const d = document;

/*export const sendClientToCita = (
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
*/
export const sendClientToCita = (
  btn_windowCite,
  window_cite,
  window_client
) => {
  document.addEventListener("click", (e) => {
    // 🟢 Cuando se hace clic en el botón "Crear Cita" de una fila
    if (e.target.matches(btn_windowCite)) {
      const fila = e.target.closest("tr");
      const idCliente = fila?.dataset.id;

      alert(`ID Cliente detectado: ${idCliente}`);
      console.log("el id de client es : ", idCliente);

      if (!idCliente) {
        console.warn("⚠️ No se encontró ID en la fila.");
        alert("Este cliente no tiene un ID válido.");
        return;
      }

      // Guardamos ese ID en el form_cliente (en su data-id)
      const formulario = document.getElementById("form_cliente");
      if (formulario) {
        formulario.dataset.id = idCliente;
        console.log(`✅ Cliente seleccionado, ID: ${idCliente}`);
      }

      // Cambiar de ventana cliente -> cita
      const $windowCite = document.querySelector(window_cite);
      const $windowClient = document.querySelector(window_client);
      if ($windowCite && $windowClient) {
        $windowCite.classList.add("active");
        $windowClient.classList.remove("active");
      }

      // Enviar ID a la tabla cita
      addIdToTable(idCliente);
    }
  });
};
