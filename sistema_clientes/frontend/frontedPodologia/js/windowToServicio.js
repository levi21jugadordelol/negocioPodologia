const d = document;

export const invocateDivServicio = (btnToServive, divServicio) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btnToServive) || e.target.closest(btnToServive)) {
      const allSections = d.querySelectorAll(".section");
      console.log(allSections);
      allSections.forEach((sec) => sec.classList.remove("active"));

      const $sectionServicio = d.querySelector(divServicio);
      console.log($sectionServicio);
      if ($sectionServicio) $sectionServicio.classList.add("active");
    }
  });
};
