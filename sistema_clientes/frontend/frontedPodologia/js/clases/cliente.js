export class Cliente {
  constructor(nombre, apellido, correo, celular, dni) {
    this._validarCadena(nombre, "nombre");
    this._validarCadena(apellido, "apellido");
    this._validarCorreo(correo);
    this._validarCelular(celular);
    this._validarDNI(dni);

    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.celular = celular;
    this.dni = dni;

    this.propiedades = new Map(); // adicionales: dirección, ocupación, etc.
    this.historial = new Set(); // historial de compras o citas
  }

  toBackendJson() {
    return {
      nombreCliente: this.nombre,
      apellidoCliente: this.apellido,
      dniCliente: this.dni,
      cellCliente: this.celular,
      correoCliente: this.correo,
    };
  }
  // ===== VALIDACIONES =====
  _validarCadena(valor, campo) {
    if (!valor || typeof valor !== "string")
      throw new Error(`${campo} debe ser una cadena no vacía`);
  }

  _validarCorreo(correo) {
    const expresion = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!expresion.test(correo))
      throw new Error("El correo no tiene un formato válido");
  }

  _validarCelular(celular) {
    const expresion = /^[0-9]{9}$/;
    if (!expresion.test(celular))
      throw new Error("El celular debe tener 9 dígitos numéricos");
  }

  _validarDNI(dni) {
    const expresion = /^[0-9]{8}$/;
    if (!expresion.test(dni))
      throw new Error("El DNI debe tener 8 dígitos numéricos");
  }

  // ===== GETTERS/SETTERS =====
  get nombreCompleto() {
    return `${this.nombre} ${this.apellido}`;
  }

  agregarPropiedad(key, value) {
    this.propiedades.set(key, value);
  }

  obtenerPropiedad(key) {
    return this.propiedades.get(key);
  }

  agregarHistorial(item) {
    this.historial.add(item);
  }

  listarHistorial() {
    return [...this.historial];
  }
}
//deseqeu que sea asi mi clase fonrted o como debo modfiicarlo para que encaje
