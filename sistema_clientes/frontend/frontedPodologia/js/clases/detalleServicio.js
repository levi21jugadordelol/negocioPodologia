export class DetalleServicio {
  constructor(servicioId, duracionTotal, motivo, productosUtilizados = []) {
    this._validarNumero(servicioId, "ID del servicio");
    this._validarNumero(duracionTotal, "duración total");
    this._validarCadena(motivo, "motivo");
    this._validarArray(productosUtilizados, "productos utilizados");

    // Validar que cada producto tenga al menos un productoId válido
    productosUtilizados.forEach((producto, i) => {
      if (
        !producto ||
        typeof producto !== "object" ||
        typeof producto.productoId !== "number" ||
        isNaN(producto.productoId) ||
        producto.productoId <= 0
      ) {
        throw new Error(`Producto en posición ${i} no es válido`);
      }
    });

    this.servicioId = servicioId;
    this.duracionTotal = duracionTotal;
    this.motivo = motivo;
    this.productosUtilizados = productosUtilizados; // [{ productoId }]
    this.extraData = new Map(); // extensible
  }

  toBackendJson() {
    return {
      servicioId: this.servicioId,
      duracionTotal: this.duracionTotal,
      motivo: this.motivo,
      productosUtilizados: this.productosUtilizados.map((p) => ({
        productoId: p.productoId,
      })),
    };
  }

  agregarProducto(productoId) {
    this._validarNumero(productoId, "ID del producto");
    this.productosUtilizados.push({ productoId });
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
