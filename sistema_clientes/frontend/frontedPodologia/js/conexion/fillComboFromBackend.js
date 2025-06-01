import { BASE_URL } from "../config/configuracion.js";

export const llenarComboEstadoCita = async (select) => {
  try {
    const response = await fetch(`${BASE_URL}/citas/estados`);
    const estados = await response.json();

    console.log("Estados recibidos del backend:", estados);
    select.innerHTML = "<option disabled selected>Seleccione estado</option>";

    estados.forEach((estado) => {
      const option = document.createElement("option");
      option.value = estado.codigo;
      option.textContent =
        estado.valor.charAt(0) + estado.valor.slice(1).toLowerCase(); // Capitaliza
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error al cargar estados de cita:", error);
  }
};

export const llenarComboTipoCita = async (select) => {
  try {
    const response = await fetch(`${BASE_URL}/citas/tipos`);
    const tipos = await response.json();

    select.innerHTML =
      "<option disabled selected>Seleccione tipo de cita</option>";
    tipos.forEach((tipo) => {
      const option = document.createElement("option");
      option.value = tipo;
      option.textContent = tipo.charAt(0) + tipo.slice(1).toLowerCase();
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error al cargar tipos de cita:", error);
  }
};
