export class DetalleServicio {
  constructor(servicioId, duracionTotal, motivo, productosUtilizados = []) {
    this._validarNumero(servicioId, "ID del servicio");
    this._validarNumero(duracionTotal, "duración total");
    this._validarCadena(motivo, "motivo");
    this._validarArray(productosUtilizados, "productos utilizados");

    this.servicioId = servicioId;
    this.duracionTotal = duracionTotal;
    this.motivo = motivo;
    this.productosUtilizados = productosUtilizados; // array de objetos con productoId y cantidad

    this.extraData = new Map(); // extensible
  }

  toBackendJson() {
    return {
      servicioId: this.servicioId,
      duracionTotal: this.duracionTotal,
      motivo: this.motivo,
      productosUtilizados: this.productosUtilizados.map((p) => ({
        productoId: p.productoId,
        cantidadUtilizada: p.cantidadUtilizada,
      })),
    };
  }

  agregarProducto(productoId, cantidadUtilizada) {
    this._validarNumero(productoId, "ID del producto");
    this._validarNumero(cantidadUtilizada, "cantidad utilizada");

    this.productosUtilizados.push({ productoId, cantidadUtilizada });
  }

  agregarDatoExtra(key, value) {
    this.extraData.set(key, value);
  }

  obtenerDatoExtra(key) {
    return this.extraData.get(key);
  }

  // === VALIDACIONES ===
  _validarCadena(valor, campo) {
    if (!valor || typeof valor !== "string")
      throw new Error(`${campo} debe ser una cadena no vacía`);
  }

  _validarNumero(valor, campo) {
    if (typeof valor !== "number" || isNaN(valor) || valor <= 0)
      throw new Error(`${campo} debe ser un número válido y positivo`);
  }

  _validarArray(valor, campo) {
    if (!Array.isArray(valor)) throw new Error(`${campo} debe ser un arreglo`);
  }
}
