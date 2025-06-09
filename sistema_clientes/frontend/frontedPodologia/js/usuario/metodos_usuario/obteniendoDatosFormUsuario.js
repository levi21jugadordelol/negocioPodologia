const d = document;

export const obteniendoDatosFormUsuario = () => {
  const $form = d.getElementById("form_registro");
  console.log("el valor de form de registro es: ", $form);

  const formData = new FormData($form);

  const datosUsuario = {
    nombreUsuario: formData.get("nombreUsuario"),
    apellidoUsuario: formData.get("apellidoUsuario"),
    email: formData.get("email"),
    contrasenia: formData.get("contrasenia"),
    contraseniaConfirmada: formData.get("contrasenia_confirmacion"),
  };

  return datosUsuario;
};
