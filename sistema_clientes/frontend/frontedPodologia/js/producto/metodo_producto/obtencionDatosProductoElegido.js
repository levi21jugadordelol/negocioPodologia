import { actualizarProductoEnMemoria } from "./actualizarProductoEnMemoria.js";

const d = document;

export const obtencionDatosProductoElegido = (idProducto) => {
  const $form_product_edit = d.getElementById("form_producto_edit");
  console.log(
    "el valor del formulario para edit producto es: ",
    $form_product_edit
  );

  if (!$form_product_edit) return;

  const producto = actualizarProductoEnMemoria().find(
    (c) => String(c.id) === String(idProducto)
  );

  if (!producto) {
    console.warn("producto no en contrado con id: ", idProducto);
    return;
  }

  console.log("el id del producto es: ", producto.idProducto);

  // Asignamos los datos al formulario
  $form_product_edit.dataset.id = producto.id;

  console.log("$form.elements: ", $form_product_edit.elements);

  console.log(
    "$form.elements['nombreProducto']: ",
    $form_product_edit.elements["modal_nombreProducto_edit"]
  );

  $form_product_edit.elements["modal_nombreProducto_edit"].value =
    producto.nombre || "";
  $form_product_edit.elements["modal_stock_edit"].value = producto.stock || "";
  $form_product_edit.elements["modal_tipoProducto_edit"].value =
    producto.tipo || "";

  console.log("producto guardado para edicion: ", producto);
  console.log("Formulario dataset id:", $form_product_edit.dataset.id);
};
