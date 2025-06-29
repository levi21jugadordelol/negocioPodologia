import { BASE_URL } from "../../config/configuracion.js";
import { comenzandoProcesoEdit } from "../metodo_producto/comenzandoProcesoEdit.js";
import { productoStorage } from "../../localStorage/productoStorage.js";
const d = document;

export const editProductoApi = async (idProducto, productoBackend) => {
  const URL_API = `${BASE_URL}/servicio/editar/${idProducto}`;
  try {
    // const respuesta = await fetch(`${BASE_URL}/producto/editar/${idProducto}`, {
    const respuesta = await fetch(URL_API, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(productoBackend),
    });

    console.log("el producto backend es: ", productoBackend);

    if (!respuesta.ok) {
      throw new Error("No se pudo editar el producto del backend");
    }

    const productoFetch = await respuesta.json();

    console.log(`objeto editado : ${JSON.stringify(productoFetch)}`);

    alert("✅ producto editado exitosamente");
    const { idProducto } = productoFetch;

    console.log(productoFetch.idProducto);

    const $formularioEdit = d.getElementById("form_producto_edit");
    console.log($formularioEdit);
    if ($formularioEdit) {
      $formularioEdit.dataset.id = idProducto;
    }

    productoBackend.idProducto = idProducto;
    /* console.log(`🗑️ Producto ${idProducto} editado del backend`);
    return true;*/
    const productoNormalizado = {
      nombreProducto: productoBackend.nombre || productoBackend.nombreProducto,
      stock: productoBackend.stock,
      tipoProducto: productoBackend.tipoProducto,
      idProducto: productoBackend.idProducto,
    };

    console.log(
      "🟢 producto normalizado que se va a guardar:",
      productoNormalizado
    );

    // 1. Guardamos producto
    productoStorage.guardarProducto(productoNormalizado);

    // 2. Obtenemos todos los productos actualizados
    const todosProductos = productoStorage.obtenerProductos();

    console.log("productos actualizados en el ls", todosProductos);

    // Opcional: verificar si el producto editado está ahí
    const productoEncontrado = todosProductos.find(
      (c) => c.idProducto === idProducto
    );
    if (productoEncontrado) {
      console.log(
        "✅ producto editado encontrado en localStorage:",
        productoEncontrado
      );
    } else {
      console.warn("❌ producto editado NO encontrado en localStorage.");
    }
    alert("✅ producto guardado exitosamente");

    //  return { exito: true, mensaje: clienteGuardado.mensaje };
    return idProducto;
  } catch (error) {
    console.error("❌ Error al editar del backend:", error.message);
    return false;
  }
};
