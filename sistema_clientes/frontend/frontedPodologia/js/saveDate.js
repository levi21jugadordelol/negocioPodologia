import { getDataFromCita } from "./getDatafromCita.js";

const d = document;
export const infoCita = (btnSelector) => {
  const d = document;
  d.addEventListener("click", (e) => {
    if (e.target.matches(btnSelector) || e.target.closest(btnSelector)) {
      getDataFromCita();
    }
  });
};
