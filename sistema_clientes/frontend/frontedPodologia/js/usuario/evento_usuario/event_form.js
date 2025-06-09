import { enviandoFormToBackend } from "../metodos_usuario/enviandoFormToBackend.js";

const d = document;

export const event_formulario = (buton_enviar) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(buton_enviar)) {
      e.preventDefault();
      // alert("funciona el button de registro");
      await enviandoFormToBackend();
    }
  });
};
