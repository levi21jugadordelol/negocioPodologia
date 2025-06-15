import { cerrandoSessionStorage } from "./metodos/cerrandoSessionStorage.js";
import { cerrandoLocalStorage } from "./metodos/cerrandoLocalStorage.js";
import { avisoConfirmacionCerrarSession } from "./metodos/avisoConfirmacionCerrarSession.js";

const d = document;

export const cerrandoSession = (btn_close_session) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(btn_close_session)) {
      // alert("funciona el button de cerrar session");
      const confirmacion = await avisoConfirmacionCerrarSession();
      console.log("el valor de la confirmacion es: ", confirmacion);

      if (confirmacion) {
        cerrandoLocalStorage();
        cerrandoSessionStorage();

        await Swal.fire({
          title: "Cerrando sesión",
          text: "Nos vemos pronto",
          icon: "info",
          timer: 1800,
          showConfirmButton: false,
        });

        window.location.href = "/html/login.html";
      }
    }
  });
};
