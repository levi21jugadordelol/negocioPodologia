const d = document;

export const tableClient = (button_open, sectionClient) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      //  alert("funcioa");
      // Ocultar todas las secciones primero
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));
      // Mostrar la sección de citas
      const $sectionCite = d.querySelector(sectionClient);
      if ($sectionCite) $sectionCite.classList.add("active");
    }
  });
};
