import { deleteProductoApi } from "../api_producto/deleteProductoApi.js";
import { productoStorage } from "../../localStorage/productoStorage.js";
import { sendDataProductToTable } from "../../sendDataProductToTable.js";
import { comenzandoProcesoEdit } from "../metodo_producto/comenzandoProcesoEdit.js";
import { obtencionDatosProductoElegido } from "../metodo_producto/obtencionDatosProductoElegido.js";

const d = document;

export const eventoEditDeleteProducto = (
  button_edit,
  button_delete,
  cerrar_ventana,
  guardar_edit
) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(button_delete)) {
      //alert("funciona el button de delete");

      console.log("🖱️ Click en botón eliminar detectado");
      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new error("no se encontro fila");
        console.log("📌 Fila HTML encontrada:", fila);

        const idProducto = parseInt(fila.dataset.id, 10);
        console.log("🔑 ID de producto a eliminar:", idProducto);

        if (isNaN(idProducto)) throw new Error("id del producto invalido");

        const confirmado = confirm("¿Estás seguro de eliminar este producto?");
        if (!confirmado) {
          console.log("❎ Eliminación cancelada por el usuario");
          return;
        }
        console.log("🔁 Intentando eliminar producto del backend...");
        const eliminado = await deleteProductoApi(idProducto);

        if (eliminado) {
          console.log("📦 Eliminando del productoStorage...");
          productoStorage.eliminarProductoPorId(idProducto); // <-- elimina del storage
          // ✅ Borra visualmente esa fila
          console.log("🧼 Eliminando visualmente la fila del DOM");
          fila.remove();
          alert("✅ Producto eliminado correctamente.");
        } else {
          alert("❌ No se pudo eliminar el producto.");
        }
      } catch (error) {
        console.error("el error es : ", error);
      }
    }
    if (e.target.matches(button_edit)) {
      // alert("funciona el button de edit");
      console.log("🖱️ Click en botón de editar detectado");

      const $ventanaModalEditProducto = d.querySelector("#modal_producto_edit");
      console.log(
        "el valor de ventana modal de edit para producto es: ",
        $ventanaModalEditProducto
      );
      $ventanaModalEditProducto.classList.remove("translate");

      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new error("no se encontro fila");
        console.log("📌 Fila HTML encontrada:", fila);

        const idProducto = parseInt(fila.dataset.id, 10);
        console.log("🔑 ID de producto a editar:", idProducto);

        if (isNaN(idProducto)) throw new Error("id del producto invalido");

        obtencionDatosProductoElegido(idProducto);
      } catch (error) {
        console.error("el error es : ", error);
      }
    }

    if (e.target.matches(guardar_edit)) {
      //alert("funciona el button deguar edit");
      e.preventDefault();
      comenzandoProcesoEdit();
    }

    if (e.target.matches(cerrar_ventana)) {
      const $ventanaModalEditProducto = d.querySelector("#modal_producto_edit");
      console.log(
        "el valor de ventana modal de edit para producto es: ",
        $ventanaModalEditProducto
      );
      $ventanaModalEditProducto.classList.add("translate");
    }
  });
};
