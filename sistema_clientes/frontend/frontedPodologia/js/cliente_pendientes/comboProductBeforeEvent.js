import { fillComboProductFromBackend } from "../conexion/fillComboProductFromBackend.js";

const d = document;
export const comboProductBeforeEvent = () => {
  const selectsProductos = d.querySelectorAll(".producto_id");
  console.log("Encontrados:", selectsProductos.length);
  selectsProductos.forEach((select) => {
    fillComboProductFromBackend(select); // ✅ ahora sí pasamos cada <select> individual
  });
};
