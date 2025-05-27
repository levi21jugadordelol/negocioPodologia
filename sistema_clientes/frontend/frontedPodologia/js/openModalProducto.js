import { getDataProduct } from "./getDataProduct.js";

const d = document;

export const openModalProducto = (
  btn_modal_product,
  window_modal_product,
  cerrar_modal,
  btn_enviar_datos_product
) => {
  d.addEventListener("click", (e) => {
    if (
      e.target.matches(btn_modal_product) ||
      e.target.closest(btn_modal_product)
    ) {
      // alert("funciona el buton para abrir el modal producto");
      const $modalProducto = d.querySelector(window_modal_product);
      $modalProducto.classList.remove("translate");
    }
    if (e.target.matches(cerrar_modal)) {
      const $modalProducto = d.querySelector(window_modal_product);
      $modalProducto.classList.add("translate");
    }
    if (e.target.matches(btn_enviar_datos_product)) {
      e.preventDefault();
      console.log("enviando datos producto");
      getDataProduct();
    }
  });
};
