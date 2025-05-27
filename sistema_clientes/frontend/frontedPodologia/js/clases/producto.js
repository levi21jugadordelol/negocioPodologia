export class Producto {
  constructor(nombre, stock, tipo) {
    this._validarCadena(nombre, "nombre del producto");
    this._validarNumero(stock, "stock del producto", true); // permite 0
    this._validarCadena(tipo, "tipo de producto");

    this.nombre = nombre;
    this.stock = stock;
    this.tipo = tipo;

    this.propiedades = new Map();
    this.historial = new Set();
  }

  toBackendJson() {
    return {
      nombreProducto: this.nombre,
      stock: this.stock,
      tipoProducto: this.tipo,
    };
  }

  // Validaciones
  _validarCadena(valor, campo) {
    if (!valor || typeof valor !== "string")
      throw new Error(`${campo} debe ser una cadena no vacía`);
  }

  _validarNumero(valor, campo, permitirCero = false) {
    if (typeof valor !== "number" || isNaN(valor) || valor < 0)
      throw new Error(`${campo} debe ser un número válido`);
    if (!permitirCero && valor === 0)
      throw new Error(`${campo} debe ser mayor que cero`);
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
