// 🔁 Función genérica para cambiar estado de botones
const toggleBoton = (selector, estado) => {
  const boton = document.querySelector(selector);
  if (boton) {
    boton.disabled = !estado;
    boton.classList.toggle("opacity-50", !estado);
    boton.classList.toggle("cursor-not-allowed", !estado);
  }
};

export const desactivarBotonGuardar = () =>
  toggleBoton(".btn-guardar-cita", false);
export const desactivarBotonEdit = () => toggleBoton(".btn-editar-cita", false);

export const activarBotonGuardar = () => toggleBoton(".btn-guardar-cita", true);

export const activarBotonEdit = () => toggleBoton(".btn-editar-cita", true);

// 🔁 Función genérica para activar/desactivar campos de una fila
const toggleCamposFila = (fila, estado) => {
  if (!fila) return;
  const inputs = fila.querySelectorAll("input, select");
  inputs.forEach((el) => {
    el.disabled = !estado;
    el.classList.toggle("opacity-50", !estado);
    el.classList.toggle("bg-light", !estado);
  });
};

export const desactivarCamposFila = (fila) => {
  fila = document.querySelector("#tabla-citas tbody")?.lastElementChild;
  toggleCamposFila(fila, false);
};

export const activarCamposFila = (fila) => {
  fila = document.querySelector("#tabla-citas tbody")?.lastElementChild;
  toggleCamposFila(fila, true);
};
