import { infoDatos } from "./obteniendoDatos.js";

const d = document;

export default function crearModalNuevoCliente(
  btn,
  panel_cliente,
  cerrar,
  btn_enviar_datos
) {
  d.addEventListener("click", (e) => {
    if (e.target.matches(btn)) {
      const $modal = d.querySelector(panel_cliente); // antes usabas mal el selector
      console.log("Abriendo modal");
      $modal.classList.remove("translate"); // abrir modal quitando la clase
    }
    if (e.target.matches(cerrar)) {
      const $modal = d.querySelector(panel_cliente);
      $modal.classList.add("translate"); // añade la clase que lo oculta
    }
    if (e.target.matches(btn_enviar_datos)) {
      e.preventDefault();
      console.log("Enviando datos...");
      infoDatos();
    }
  });
}
