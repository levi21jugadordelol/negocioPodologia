import { actualizarServicioEnMemoria } from "../metodos/actualizarServicioEnMemoria.js";

const d = document;

export const obtencionDatosServicioToEdit = (idServicio) => {
  const $form_servicio_edit = d.getElementById("form_servicio_edit");
  console.log(
    "el valor del formulario para edit servicio es: ",
    $form_servicio_edit
  );

  if (!$form_servicio_edit) return;

  const servicio = actualizarServicioEnMemoria().find(
    (c) =>
      String(c.id) === String(idServicio) ||
      String(c.idServicio) === String(idServicio)
  );

  if (!servicio) {
    console.warn("servicio no en contrado con id: ", idServicio);
    return;
  }

  console.log("el id del producto es: ", servicio.idServicio);

  //asignamos los datos al form
  $form_servicio_edit.dataset.id = servicio.id;

  console.log("$form.elements: ", $form_servicio_edit.elements);

  console.log(
    "$form.elements['nombreServicio']: ",
    $form_servicio_edit.elements["modal_nombreServicio_edit"]
  );

  $form_servicio_edit.elements["modal_nombreServicio_edit"].value =
    servicio.nombre || "";
  $form_servicio_edit.elements["modal_precio_edit"].value =
    servicio.precio || "";
  $form_servicio_edit.elements["modal_descripcion_edit"].value =
    servicio.descripcion || "";
  $form_servicio_edit.elements["modal_duracion_edit"].value =
    servicio.duracion || "";

  console.log("servicio guardado para edicion: ", servicio);
  console.log("Formulario dataset id:", $form_servicio_edit.dataset.id);
};
