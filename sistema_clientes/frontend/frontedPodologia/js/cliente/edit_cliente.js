import { capturaInfoClientEdit } from "./capturarInfoClientEdit.js";
import { getDataClientToEdit } from "./getDataClientToEdit.js";

const d = document;

//.click_editar
//modal-overlay_edit
export const edit_cliente = (
  abrir_edit_client,
  ventana_edit_cliente,
  cerrar,
  enviar_edit
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(abrir_edit_client)) {
      const $modal_edit = d.querySelector(ventana_edit_cliente);
      //console.log($modal_edit);
      $modal_edit.classList.remove("translate");

      // ✅ Extraer ID del cliente desde la fila contenedora
      const fila = e.target.closest("tr");
      const idCliente = fila?.dataset.id;
      console.log("el id del cliente desde el lugar del evento : ", idCliente);
      /*const $form = document.getElementById("form_cliente_edit");
      $form.dataset.id = idCliente;
      //enviamos el id a la  ventana modal */
      getDataClientToEdit(idCliente);
    }
    if (e.target.matches(cerrar)) {
      const $modal_edit = d.querySelector(ventana_edit_cliente);
      //console.log($modal_edit);
      $modal_edit.classList.add("translate");
    }
    if (e.target.matches(enviar_edit)) {
      e.preventDefault();
      capturaInfoClientEdit();
    }
  });
};
