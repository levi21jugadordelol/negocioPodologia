import { postDataDetails } from "./postDataDetails.js";

let currentCitaId = null;

const d = document;
export const openModalDetailsService = (
  buton_open_details,
  modal_details,
  close_modal,
  btn_save_details
) => {
  d.addEventListener("click", (e) => {
    // Abrir modal con ID
    if (e.target.matches(buton_open_details)) {
      //dataset es una propiedad especial de los elementos del DOM que te permite acceder a todos los atributos que empiezan con data- en tu HTML.
      console.log(
        "el click que le damos le pertenece a : " + buton_open_details
      );
      console.log("Dataset del target:", e.target.dataset);

      const $modalDetails = d.querySelector(modal_details);
      $modalDetails.classList.remove("translate");
      // 🟡 Guardar el ID de la cita en variable
      currentCitaId = e.target.dataset.idCita;

      console.log("Cita activa:", currentCitaId);
      // ⬇️ INYECTAR el ID y nombre del servicio en el modal
      const servicioId = e.target.dataset.servicioId;
      const servicioNombre = e.target.dataset.servicioNombre;

      d.getElementById("modal_servicio_id").value = servicioId;
      d.getElementById("modal_servicio_nombre").value = servicioNombre;

      console.log("Servicio cargado:", servicioId, servicioNombre);
    }
    if (e.target.matches(close_modal)) {
      const $modalProducto = d.querySelector(modal_details);
      $modalProducto.classList.add("translate");
    }
    if (e.target.matches(btn_save_details)) {
      e.preventDefault();
      console.log("enviando datos producto");
      console.log("Enviando detalle para cita:", currentCitaId);
      postDataDetails(currentCitaId);
    }
  });
};
