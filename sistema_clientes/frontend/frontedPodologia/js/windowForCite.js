const d = document;
const ss = sessionStorage;
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
          ss.setItem("ventana_cita", "activo"); // ✅ Guardamos la sección activa
          console.log(
            "💾 Estado guardado en localStorage: ventana_cita = activo"
          );
        } catch (err) {
          console.error("❌ Error al validar cliente:", err.message);
          alert("ID de cliente inválido. El cliente no existe.");
        }
      })();

      // addIdToTable(clienteId);
    }
  });
};

d.addEventListener("DOMContentLoaded", () => {
  const seccionGuardada = ss.getItem("ventana_cita");
  console.log("🟡 Estado de ventana_cita:", seccionGuardada); // Debug info

  if (seccionGuardada === "activo") {
    const allSections = d.querySelectorAll(".section");
    allSections.forEach((sec) => sec.classList.remove("active"));

    const $sectionCite = d.querySelector("#vista-citas");
    if ($sectionCite) {
      $sectionCite.classList.add("active");
      console.log("✅ Sección Cita activada automáticamente");
    } else {
      console.error("❌ No se encontró la sección con ID #section-cita");
    }
  }
});
