//import { productoStorage } from "./productoStorage.js";
import { productoStorage } from "../localStorage/productoStorage.js";
//import { datosProducto } from "./datosProducto.js";
import { datosProducto } from "../getDataProduct.js";
//import { sendDataProductToTable } from "./sendDataProductToTable.js";
import { sendDataProductToTable } from "../sendDataProductToTable.js";

export function recuperarProductosDesdeLocalStorage() {
  datosProducto.length = 0;

  const productos = productoStorage.obtenerProductos();
  console.log("📦 Productos recuperados desde localStorage:", productos);

  datosProducto.push(...productos);

  console.log("📂 datosProducto actual:", datosProducto);

  sendDataProductToTable();
  return datosProducto;
}
