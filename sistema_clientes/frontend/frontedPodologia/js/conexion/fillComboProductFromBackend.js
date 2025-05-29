import { BASE_URL } from "../config/configuracion.js";

export const fillComboProductFromBackend = async (select) => {
  try {
    const response = await fetch(`${BASE_URL}/producto/listar`);

    if (!response.ok) throw new Error("error al obtener productos");

    const productos = await response.json();

    select.innerHTML = "<option disabled selected>Seleccione servicio</option>";

    productos.forEach((producto) => {
      const option = document.createElement("option");
      option.value = producto.idProducto;
      option.textContent = producto.nombreProducto;
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error al cargar productos:", error);
  }
};
