const d = document;
const c = console;

export const capturandoNombreUsuario = () => {
  const $formRegistro = document.getElementById("form_registro");
  const formData = new FormData($formRegistro);
  const nombreUsuario = formData.get("nombreUsuario");
  return nombreUsuario;
};
