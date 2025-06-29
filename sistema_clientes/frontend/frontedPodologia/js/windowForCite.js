const d = document;
const ss = sessionStorage;
//import { BASE_URL } from "./config/configuracion.js";
import { traerCitasDelDia } from "./citas/metodos/traerCitasDelDia.js";

export const tableCita = (button_open, sectionCite) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      // Ocultar todas las secciones primero
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));

      // Mostrar la sección de citas
      const $sectionCite = d.querySelector(sectionCite);
      console.log("el valor de $sectionCite es: ", $sectionCite);
      if ($sectionCite) $sectionCite.classList.add("active");

      traerCitasDelDia();
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
