import { Producto } from "./clases/producto.js";
import { enviarProductoApi } from "./conexion/enviarProductoApi.js";
import { sendDataProductToTable } from "./sendDataProductToTable.js";

export const datosProducto = [];

export const getDataProduct = async () => {
  const d = document;
  const $formProducto = d.getElementById("form_producto");

  const nombreProducto = d.getElementById("modal_nombreProducto").value.trim();
  const stock = Number(d.getElementById("modal_stock").value);
  const tipoProducto = d.getElementById("modal_tipoProducto").value.trim();

  try {
    const producto = new Producto(nombreProducto, stock, tipoProducto);
    datosProducto.push(producto);
    console.log("📦 Producto agregado:", producto);

    const respuesta = await enviarProductoApi(producto.toBackendJson());

    if (respuesta.error) {
      alert("❌ Error al enviar al backend: " + respuesta.mensaje);
    } else {
      alert("✅ Producto enviado exitosamente.");
      producto.id = respuesta.idProducto || null;
    }
  } catch (error) {
    alert("❌ Error al crear producto: " + error.message);
  }

  console.log("📋 Lista de productos:", datosProducto);
  $formProducto.reset();
  sendDataProductToTable();
};
