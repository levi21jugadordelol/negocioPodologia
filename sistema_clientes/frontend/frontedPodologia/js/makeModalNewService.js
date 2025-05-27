import { infoDatosService } from "./getDateServiceFromModal.js";

const d = document;

export const createModalService = (
  button_crear,
  modal_service,
  button_close,
  button_enviar
) => {
  d.addEventListener("click", (e) => {
    if (e.target.matches(button_crear)) {
      const $modal_service = d.querySelector(modal_service);
      $modal_service.classList.remove("translate");
    }
    if (e.target.matches(button_close)) {
      const $modal_service = d.querySelector(modal_service);
      $modal_service.classList.add("translate");
    }
    if (e.target.matches(button_enviar)) {
      e.preventDefault();
      console.log("enviando datos del servicio");
      infoDatosService();
    }
  });
};
