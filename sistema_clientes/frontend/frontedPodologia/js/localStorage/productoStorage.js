import { Producto } from "../clases/producto.js";

const STORAGE_KEY = "producto_storage";

export class ProductoStorage {
  constructor() {
    if (ProductoStorage._instancia) {
      return ProductoStorage._instancia;
    }
    ProductoStorage._instancia = this;
  }

  guardarProducto(producto) {
    const productos = this.obtenerProductos();
    console.log(
      "los productos en guardar producto desde ls en el metodo guardarProducto() es: ",
      productos
    );
    let id = producto.idProducto || producto.id;

    if (!id) {
      id = Date.now();
    }

    producto.idProducto = id;
    producto.id = id;

    const index = productos.findIndex(
      (p) =>
        (p.idProducto && String(p.idProducto) === String(id)) ||
        (p.id && String(p.id) === String(id))
    );

    if (index >= 0) {
      productos[index] = producto;
    } else {
      productos.push(producto);
    }

    localStorage.setItem(STORAGE_KEY, JSON.stringify(productos));
  }

  guardarTodosLosProductos(listaProductos) {
    const productosConvertidos = listaProductos.map((obj) => {
      const producto = { ...obj };

      let id = producto.idProducto || producto.id;
      if (!id) {
        id = Date.now() + Math.random(); // Evita colisiones
      }

      producto.idProducto = id;
      producto.id = id;

      return producto;
    });

    localStorage.setItem(STORAGE_KEY, JSON.stringify(productosConvertidos));
  }

  obtenerProductos() {
    const data = localStorage.getItem(STORAGE_KEY);
    const lista = data ? JSON.parse(data) : [];

    console.log(
      "la lista de productos en el ls, en el metodo obtenerProductos() es: ",
      lista
    );
    return lista
      .filter(
        (obj) =>
          typeof obj.nombreProducto === "string" &&
          typeof obj.tipoProducto === "string" &&
          !isNaN(obj.stock)
      )
      .map((obj) => {
        const producto = new Producto(
          obj.nombreProducto,
          obj.stock,
          obj.tipoProducto
        );
        producto.id = obj.idProducto || obj.id;
        producto.idProducto = obj.idProducto || obj.id;

        return producto;
      });
  }

  eliminarProductoPorId(id) {
    const productos = this.obtenerProductos();
    const filtrados = productos.filter((p) => String(p.id) !== String(id));
    localStorage.setItem(STORAGE_KEY, JSON.stringify(filtrados));
  }

  vaciarProductos() {
    localStorage.removeItem(STORAGE_KEY);
  }
}

export const productoStorage = new ProductoStorage();
