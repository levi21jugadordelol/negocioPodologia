const d = document;

export const tableCita = (button_open, sectionCite) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      // Ocultar todas las secciones primero
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));

      // Mostrar la sección de citas
      const $sectionCite = d.querySelector(sectionCite);
      if ($sectionCite) $sectionCite.classList.add("active");
    }
  });
};
