import { getDataFromCita } from "./getDatafromCita.js";

const d = document;
export const infoCita = (btnSelector) => {
  const d = document;
  d.addEventListener("click", (e) => {
    if (e.target.matches(btnSelector) || e.target.closest(btnSelector)) {
      // Aquí va tu lógica async
      //alert("funciona");
      getDataFromCita();
      // Ejemplo de futura lógica async:
      // const resultado = await fetchData();
      // console.log(resultado);
    }
  });
};
