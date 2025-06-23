import { servicioStorage } from "../../localStorage/servicioStorage.js";
import { deleteServicioApi } from "../api_servicios/deleteServicioApi.js";
import { obtencionDatosServicioToEdit } from "../metodos/obtencionDatosServicioToEdit.js";
import { comenzandoProcesoServicioEdit } from "./comenzandoProcesoServicioEdit.js";

const d = document;

//click_editar_servicio - click_delete_servicio
export const eventoEditDeleteServicio = (
  btn_edit,
  btn_eliminar,
  btn_enviar,
  btn_cerrar
) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(btn_edit)) {
      //alert("funciona el button de edit de servicio");
      console.log("🖱️ Click en botón de editar detectado");

      const $ventanaModalEditServicio = d.querySelector("#modal_servicio_edit");

      console.log(
        "el valor de ventana modal para edit servicio es: ",
        $ventanaModalEditServicio
      );

      $ventanaModalEditServicio.classList.remove("translate");
      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new error("no se encontro fila");
        console.log("📌 Fila HTML encontrada:", fila);

        const idServicio = parseInt(fila.dataset.id, 10);
        console.log("🔑 ID de servicio a editar:", idServicio);

        if (isNaN(idServicio)) throw new Error("id del servicio invalido");
        obtencionDatosServicioToEdit(idServicio);
      } catch (error) {
        console.error("el error es : ", error);
      }
    }

    if (e.target.matches(btn_eliminar)) {
      console.log("🖱️ Click en botón eliminar detectado");
      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new Error("No se encontró la fila");

        const idServicio = parseInt(fila.dataset.id, 10);
        if (isNaN(idServicio)) throw new Error("ID del servicio inválido");

        const confirmado = confirm("¿Estás seguro de eliminar este servicio?");
        if (!confirmado) {
          console.log("❎ Eliminación cancelada por el usuario");
          return;
        }

        console.log("🔁 Intentando eliminar servicio del backend...");
        const { success, mensaje } = await deleteServicioApi(idServicio);

        if (success) {
          servicioStorage.eliminarServicioPorId(idServicio);
          fila.remove();
          alert("✅ " + mensaje);
        } else {
          alert("❌ " + mensaje);
        }
      } catch (error) {
        console.error("❌ Error inesperado:", error);
        alert("⚠️ Error inesperado: " + error.message);
      }
    }

    if (e.target.matches(btn_enviar)) {
      //alert("funciona el button de enviar edit");
      e.preventDefault();
      comenzandoProcesoServicioEdit();
    }
    if (e.target.matches(btn_cerrar)) {
      const $ventanaModalEditServicio = d.querySelector("#modal_servicio_edit");
      console.log(
        "el valor de ventana modal de edit para producto es: ",
        $ventanaModalEditServicio
      );
      $ventanaModalEditServicio.classList.add("translate");
    }
  });
};
