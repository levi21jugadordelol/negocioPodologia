export class Usuario {
  constructor(
    nombreUsuario,
    apellidoUsuario,
    email,
    contrasenia,
    contraseniaConfirmada
  ) {
    this._validarCampo(nombreUsuario, "Nombre");
    this._validarCampo(apellidoUsuario, "Apellido");
    this._validarEmail(email);
    this._validarPassword(contrasenia, contraseniaConfirmada);

    this.nombreUsuario = nombreUsuario;
    this.apellidoUsuario = apellidoUsuario;
    this.email = email;
    this.contrasenia = contrasenia;
    this.contraseniaConfirmada = contraseniaConfirmada;
  }

  toBackendJson() {
    return {
      nombreUsuario: this.nombreUsuario,
      apellidoUsuario: this.apellidoUsuario,
      email: this.email,
      contrasenia: this.contrasenia,
      contraseniaConfirmada: this.contraseniaConfirmada,
    };
  }

  _validarCampo(valor, campo) {
    if (!valor || typeof valor !== "string") {
      throw new Error(`${campo} es obligatorio y debe ser texto`);
    }
  }

  _validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!regex.test(email)) {
      throw new Error("El email no tiene un formato válido");
    }
  }

  _validarPassword(pass, passConfirm) {
    if (pass.length < 6) {
      throw new Error("La contraseña debe tener al menos 6 caracteres");
    }
    if (pass !== passConfirm) {
      throw new Error("Las contraseñas no coinciden");
    }
  }
}
