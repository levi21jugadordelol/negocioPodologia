import { BASE_URL } from "../../../config/configuracion.js";

const d = document;

export async function endPointExcel() {
  const URL_API = `${BASE_URL}/cliente/export-excel`;

  try {
    const respuesta = await fetch(URL_API, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.token}`, // si usas seguridad
      },
    });

    if (!respuesta.ok) {
      throw new Error(`Error al exportar Excel: ${respuesta.status}`);
    }

    const blob = await respuesta.blob();

    // Crear un enlace y forzar la descarga
    const url = window.URL.createObjectURL(blob); //→ Crea una URL temporal para el archivo.
    const a = document.createElement("a");
    a.href = url;
    a.download = "clientes.xlsx"; //→ Define el nombre del archivo descargado.
    document.body.appendChild(a);
    a.click(); //→ Fuerza la descarga del archivo.
    a.remove();
    window.URL.revokeObjectURL(url); // liberar memoria
    return true;
  } catch (error) {
    console.error("❌ Error al exportar Excel:", error.message);
    alert("❌ No se pudo descargar el archivo Excel.");
    return false;
  }
}
