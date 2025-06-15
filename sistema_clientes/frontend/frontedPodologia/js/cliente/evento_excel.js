import { empezandoEjecucionDatosToExcel } from "./metodo/empezandoEjecucionDatosToExcel.js";
const d = document;

export const evento_excel = (button_excel) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_excel)) {
      // alert("funciona el button de exccel");
      empezandoEjecucionDatosToExcel();
    }
  });
};
