import { productoStorage } from "../../localStorage/productoStorage.js";
import { Producto } from "../../clases/producto.js";
//import editProductoApi from "../api_producto/editProductoApi.js";
import { editProductoApi } from "../api_producto/editProductoApi.js";
import { sendDataProductToTable } from "../../sendDataProductToTable.js";
import { recuperarProductosDesdeLocalStorage } from "../recuperarProductosDesdeLocalStorage.js";
import { cargarProductosDesdeLocalStorage } from "../../localStorage/cargarProductosDesdeLocalStorage.js";
const d = document;

export const comenzandoProcesoEdit = async () => {
  const $formularioEdit = d.getElementById("form_producto_edit");

  console.log("el valor del formulario edit es: ", $formularioEdit);

  const id_producto = $formularioEdit.dataset.id;

  console.log(id_producto);

  const nombre_producto = d
    .getElementById("modal_nombreProducto_edit")
    .value.trim();

  const stock_producto = Number(
    d.getElementById("modal_stock_edit").value.trim()
  );

  const tipo_producto = d
    .getElementById("modal_tipoProducto_edit")
    .value.trim();

  // ✅ Crea instancia para validar
  let productoJs;

  try {
    productoJs = new Producto(nombre_producto, stock_producto, tipo_producto);
  } catch (error) {
    alert("❌ Datos inválidos: " + error.message);
    return;
  }

  const productBackend = productoJs.toBackendJson();

  console.log("el cliente editado es: ", productBackend);

  if (id_producto) {
    // await editProductoApi(id_producto, productBackend);
    await editProductoApi(id_producto, productBackend);
    await cargarProductosDesdeLocalStorage();
  } else {
    console.warn("No se puede editar: no hay ID de producto");
  }

  $formularioEdit.reset();

  recuperarProductosDesdeLocalStorage();
};
