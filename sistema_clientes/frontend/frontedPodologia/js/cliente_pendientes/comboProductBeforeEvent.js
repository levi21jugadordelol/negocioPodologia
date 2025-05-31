import { fillComboProductFromBackend } from "../conexion/fillComboProductFromBackend.js";

import { productosUtilizados } from "./openModalDetailsService.js";

const d = document;
export const comboProductBeforeEvent = () => {
  // Array acumulador global
  const selectProducto = d.getElementById("select_producto");
  const btnAddProducto = d.getElementById("add_producto");
  const listaVisual = d.getElementById("lista_productos");
  console.log("los productos utilizados es: ", productosUtilizados);

  if (!selectProducto) {
    console.error("No se encontró el select con id 'select_producto'");
    return;
  }

  /*selectsProductos.forEach((select) => {
   // ✅ ahora sí pasamos cada <select> individual
  }); */
  fillComboProductFromBackend(selectProducto);

  btnAddProducto.addEventListener("click", () => {
    const idProducto = parseInt(selectProducto.value);

    if (!isNaN(idProducto) && idProducto > 0) {
      productosUtilizados.push({ productoId: idProducto });

      // Mostrarlo visualmente (opcional)
      const li = document.createElement("li");
      li.textContent = `Producto ID: ${idProducto}`;
      listaVisual.appendChild(li);

      // Reiniciar combo
      selectProducto.selectedIndex = 0; // o lo que necesites
    } else {
      alert("Selecciona un producto válido antes de agregar.");
    }
  });
};
