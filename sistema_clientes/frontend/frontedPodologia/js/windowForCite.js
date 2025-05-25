const d = document;
import { addIdToTable } from "./addIdToCita.js";
export const tableCita = (button_open, sectionCite) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      // Ocultar todas las secciones primero
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));

      // Mostrar la sección de citas
      const $sectionCite = d.querySelector(sectionCite);
      if ($sectionCite) $sectionCite.classList.add("active");

      // ✅ Obtener el idCliente desde el formulario
      const formulario = document.getElementById("form_cliente");
      const idCliente = formulario?.dataset.id;

      if (idCliente) {
        console.log(`✔️ ID de cliente obtenido correctamente: "${idCliente}"`);
        addIdToTable(idCliente);
      } else {
        console.warn(
          "❌ No se encontró el ID del cliente. Primero debes registrar al cliente."
        );
        alert("Primero debes registrar al cliente.");
      }

      // addIdToTable(clienteId);
    }
  });
};
