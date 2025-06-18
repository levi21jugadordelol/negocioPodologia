import { Servicio } from "../../clases/servicio.js";
import { editProductoApi } from "../../producto/api_producto/editProductoApi.js";
import { cargarServiciosDesdeLocalStorage } from "../../localStorage/cargarServiciosDesdeLocalStorage.js";

const d = document;

export const comenzandoProcesoServicioEdit = async () => {
  const $formularioServicioEdit = d.getElementById("form_servicio_edit");

  console.log(
    "el valor del formulario de servicio para edit es: ",
    $formularioServicioEdit
  );

  const id_servicio = $formularioServicioEdit.dataset.id;

  console.log("el id de servicio es: ", id_servicio);

  const nombre_servicio = d
    .getElementById("modal_nombreServicio_edit")
    .value.trim();

  const precio_servicio = Number(
    d.getElementById("modal_precio_edit").value.trim()
  );

  const descripcion_servicio = d
    .getElementById("modal_descripcion_edit")
    .value.trim();

  const duracion_servicio = Number(
    d.getElementById("modal_duracion_edit").value.trim()
  );

  // ✅ Crea instancia para validar
  let servicioJs;

  try {
    servicioJs = new Servicio(
      nombre_servicio,
      precio_servicio,
      descripcion_servicio,
      duracion_servicio
    );
  } catch (error) {
    alert("❌ Datos inválidos: " + error.message);
    return error.message;
  }

  const servicioBackend = servicioJs.toBackendJson();
  console.log("el servicio editado es: ", servicioBackend);

  if (id_servicio) {
    await editProductoApi(id_servicio, servicioBackend);
    await cargarServiciosDesdeLocalStorage();
  } else {
    console.warn("No se puede editar: no hay ID de servicio");
  }
};
