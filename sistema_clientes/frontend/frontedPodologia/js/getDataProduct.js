import { Producto } from "./clases/producto.js";
import { enviarProductoApi } from "./conexion/enviarProductoApi.js";
import { sendDataProductToTable } from "./sendDataProductToTable.js";
import { productoStorage } from "./localStorage/productoStorage.js";

export const datosProducto = [];

export const getDataProduct = async () => {
  const d = document;
  const $formProducto = d.getElementById("form_producto");

  const nombreProducto = d.getElementById("modal_nombreProducto").value.trim();
  const stock = Number(d.getElementById("modal_stock").value);
  const tipoProducto = d.getElementById("modal_tipoProducto").value.trim();

  console.log("📝 Valores del formulario:");
  console.log("Nombre:", nombreProducto);
  console.log("Stock:", stock);
  console.log("Tipo:", tipoProducto);

  try {
    const producto = new Producto(nombreProducto, stock, tipoProducto);
    console.log("✅ Instancia Producto creada:", producto);

    // Enviar a backend
    const respuesta = await enviarProductoApi(producto.toBackendJson());
    console.log("📤 Respuesta del backend:", respuesta);

    if (respuesta.error) {
      alert("❌ Error al enviar al backend: " + respuesta.mensaje);
    } else {
      alert("✅ Producto enviado exitosamente.");
      producto.id = respuesta.idProducto || null;
      producto.idProducto = respuesta.idProducto || null;
      console.log("🆔 ID asignado por backend:", producto.idProducto);
    }

    // Guardar en array local
    datosProducto.push(producto);
    console.log("🗃️ Producto guardado en datosProducto");
    console.table(datosProducto);

    // Guardar en localStorage
    productoStorage.guardarProducto(producto);
    console.log("💾 Producto guardado en localStorage");

    // Mostrar en tabla
    sendDataProductToTable();
  } catch (error) {
    alert("❌ Error al crear producto: " + error.message);
    console.error("⛔ Error en getDataProduct:", error);
  }

  $formProducto.reset();
};
