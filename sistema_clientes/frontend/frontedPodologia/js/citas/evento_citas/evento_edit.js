import { getDataCiteDorEdit } from "../getDataCiteForEdit.js";
import {
  activarBotonGuardar,
  activarCamposFila,
  desactivarBotonEdit,
  desactivarBotonGuardar,
  activarBotonEdit,
  desactivarCamposFila,
} from "../metodos/desactivar_button_input.js";

const d = document;

//btn-editar-cita
export const evento_edit = (button_editar, btn_guardar_citaEditada) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(button_editar)) {
      alert("creando evento edit");

      try {
        const fila = e.target.closest("tr"); // ⬅️ obtenemos la fila
        if (!fila) throw new Error("No se encontró la fila");
        console.log("el valor de fila es: ", fila);

        const idCita = parseInt(fila.dataset.id, 10); // ⬅️ capturamos el id desde data-id
        if (isNaN(idCita)) throw new Error("ID de cita inválido");

        console.log("🔑 ID de la cita a editar:", idCita); // ⬅️ verificación

        // Desactivar botón editar y activar guardar
        desactivarBotonEdit();
        // activarBotonGuardar();

        // 🔽 Ocultar botón Guardar al hacer clic en Editar
        const btnGuardar = fila.querySelector(".btn-guardar-cita");
        const btnGuardarEdit = fila.querySelector(".btn-guardar-edit");
        if (btnGuardar) {
          btnGuardar.classList.add("d-none");
        }

        if (btnGuardarEdit) {
          btnGuardarEdit.classList.remove("d-none");
        }

        // Habilitar campos
        activarCamposFila(fila);

        fila.dataset.editing = idCita; // para identificarla luego

        /*const datosEdit = await getDataCiteDorEdit(fila, idCita);
        console.log("datos a editar: ", datosEdit); */
      } catch (err) {
        console.error("❌ Error obteniendo datos de cita para editar:", err);
      }
    }
    if (e.target.matches(".btn-guardar-edit")) {
      console.log("🔔 Botón 'Guardar cambios' presionado");

      const botonEditar = d.querySelector(button_editar);
      if (!botonEditar) {
        console.warn("⚠️ No se encontró el botón editar en el DOM");
        return;
      }

      // Validar si el botón editar está deshabilitado (modo edición activo)
      if (!botonEditar.disabled) {
        console.warn("⚠️ No puedes guardar si no estás en modo de edición");
        return;
      }

      try {
        const fila = e.target.closest("tr");
        if (!fila) {
          throw new Error("❌ No se encontró la fila asociada al botón");
        }
        console.log("📋 Fila encontrada para guardar cambios:", fila);

        const id_cita = parseInt(fila.dataset.editing, 10);
        if (isNaN(id_cita)) {
          throw new Error(
            "❌ ID de cita inválido o no presente en data-editing"
          );
        }
        console.log("🔑 ID de cita a guardar:", id_cita);

        // Llamada asíncrona para guardar o actualizar datos
        await getDataCiteDorEdit(fila, id_cita);
        console.log("✅ Datos guardados correctamente");

        // Limpiar estado edición
        fila.removeAttribute("data-editing");

        // Cambiar visibilidad y estado de botones (puedes ajustar estas funciones)
        desactivarBotonGuardar(); // debería ocultar btn-guardar-edit y mostrar btn-guardar normal
        activarBotonEdit(); // debería activar el botón editar
        desactivarCamposFila(fila); // deshabilitar inputs para evitar edición

        const btnGuardarEdit = fila.querySelector(".btn-guardar-edit");
        const btnGuardar = fila.querySelector(".btn-guardar-cita");

        btnGuardarEdit.classList.add("d-none");

        btnGuardar.classList.remove("d-none");

        alert("✅ Cambios guardados con éxito");
      } catch (err) {
        console.error("❌ Error al guardar cambios:", err);
      }
    }
  });
};
