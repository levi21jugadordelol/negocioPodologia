const d = document;
import { addIdToTable } from "./addIdToCita.js";
import { BASE_URL } from "./config/configuracion.js";

const validarCliente = async (id) => {
  const res = await fetch(`${BASE_URL}/cliente/${id}`);
  if (!res.ok) throw new Error("Cliente no encontrado");
  return await res.json();
};

export const tableCita = (button_open, sectionCite) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      // Ocultar todas las secciones primero
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));

      // Mostrar la sección de citas
      const $sectionCite = d.querySelector(sectionCite);
      if ($sectionCite) $sectionCite.classList.add("active");

      // ✅ Lógica asincrónica separada
      (async () => {
        const formulario = document.getElementById("form_cliente");
        const idCliente = formulario?.dataset.id;

        if (!idCliente) {
          alert("Primero debes registrar al cliente.");
          return;
        }

        try {
          const cliente = await validarCliente(idCliente);
          console.log("✔️ Cliente válido:", cliente);
          addIdToTable(idCliente);
        } catch (err) {
          console.error("❌ Error al validar cliente:", err.message);
          alert("ID de cliente inválido. El cliente no existe.");
        }
      })();

      // addIdToTable(clienteId);
    }
  });
};
