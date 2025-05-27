import { BASE_URL } from "../config/configuracion.js";

export const llenarComboServicio = async (select) => {
  try {
    const response = await fetch(`${BASE_URL}/servicio/listarServicios`);
    if (!response.ok) throw new Error("Error al obtener servicios");

    const servicios = await response.json();

    select.innerHTML = "<option disabled selected>Seleccione servicio</option>";

    servicios.forEach((servicio) => {
      const option = document.createElement("option");
      option.value = servicio.idServicio;
      option.textContent = servicio.nombreServicio;
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error al cargar servicios:", error);
  }
};
