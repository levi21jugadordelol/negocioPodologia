const d = document;
const c = console;
export const capturandoValoresLogin = () => {
  const $form_login = d.getElementById("form_login");
  c.log("el valor del formulario es: ", $form_login);
  const email = d.getElementById("txtUsuario").value.trim();
  const password = d.getElementById("txtPassword").value.trim();

  c.log("email:", email);
  c.log("Contraseña:", password);

  // Opcional: Retornar los datos si los necesitas en otro módulo
  return { email, password };
};
