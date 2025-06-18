import { datosProducto } from "./getDataProduct.js";
import { productoStorage } from "./localStorage/productoStorage.js";

export const sendDataProductToTable = () => {
  const d = document;
  const tableDataProduct = d.getElementById("tabla-productos");

  tableDataProduct.innerHTML = "";

  datosProducto.forEach((producto) => {
    console.log(
      "Nombre del producto: ",
      producto.nombre,
      "Stock producto: ",
      producto.stock,
      "Tipo de producto: ",
      producto.tipo
    );

    const fila = document.createElement("tr");
    fila.dataset.id = producto.id;

    fila.innerHTML = `
      <td>${producto.nombre}</td>
      <td>${producto.stock}</td>
      <td>${producto.tipo}</td>
    `;

    const celdaAcciones = d.createElement("td");

    // Crear botones
    const botonEditar = d.createElement("button");
    const buttonDeleteProducto = d.createElement("button");

    // Texto
    botonEditar.textContent = "Editar";
    buttonDeleteProducto.textContent = "Eliminar";

    [botonEditar, buttonDeleteProducto].forEach((btn) => {
      btn.classList.add("action-button");
    });

    // Clases específicas
    botonEditar.classList.add("click_editar_producto", "green");
    buttonDeleteProducto.classList.add("click_delete_producto", "red");

    // Agregar botones a celda
    celdaAcciones.appendChild(botonEditar);
    celdaAcciones.appendChild(buttonDeleteProducto);
    fila.appendChild(celdaAcciones);

    tableDataProduct.appendChild(fila);
  });
  console.log(
    "✅ Tabla actualizada en el DOM con productos:",
    productoStorage.obtenerProductos()
  );
};
