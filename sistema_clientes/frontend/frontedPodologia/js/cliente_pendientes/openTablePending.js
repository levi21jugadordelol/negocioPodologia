import { citaFinalizada } from "../citas/api_cita/citaFinalizadaApi.js";
import { getCitePending } from "./getCitePending.js";

const d = document;

export const openTablePending = (
  button_open,
  table_pending,
  button_finalizado,
  table_finish
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_open) || e.target.closest(button_open)) {
      // alert("funciona button lista de pendientes");
      const $allSections = d.querySelectorAll(".section");
      $allSections.forEach((div) => div.classList.remove("active"));

      const $sectionTablePending = d.querySelector(table_pending);
      if ($sectionTablePending) $sectionTablePending.classList.add("active");
      getCitePending();
    }
    if (
      e.target.matches(button_finalizado) ||
      e.target.closest(button_finalizado)
    ) {
      const $allSections = d.querySelectorAll(".section");
      $allSections.forEach((div) => div.classList.remove("active"));

      const $sectionTableFinish = d.querySelector(table_finish);
      if ($sectionTableFinish) $sectionTableFinish.classList.add("active");
      citaFinalizada();
    }
  });
};
