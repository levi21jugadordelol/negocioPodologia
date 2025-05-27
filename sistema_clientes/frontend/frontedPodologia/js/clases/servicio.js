export class Servicio {
  constructor(nombre, precio, descripcion, duracion) {
    this._validarCadena(nombre, "nombre del servicio");
    this._validarNumero(precio, "precio del servicio");
    this._validarCadena(descripcion, "descripción del servicio");
    this._validarNumero(duracion, "duración del servicio");

    this.nombre = nombre;
    this.precio = precio;
    this.descripcion = descripcion;
    this.duracion = duracion; // duracion en minutos

    this.propiedades = new Map();
    this.historial = new Set();
  }

  toBackendJson() {
    return {
      nombreServicio: this.nombre,
      precioServicio: this.precio,
      descripcionServicio: this.descripcion,
      duracionServicio: this.duracion,
    };
  }

  // Validaciones
  _validarCadena(valor, campo) {
    if (!valor || typeof valor !== "string")
      throw new Error(`${campo} debe ser una cadena no vacía`);
  }

  _validarNumero(valor, campo) {
    if (typeof valor !== "number" || isNaN(valor) || valor <= 0)
      throw new Error(`${campo} debe ser un número válido y positivo`);
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
