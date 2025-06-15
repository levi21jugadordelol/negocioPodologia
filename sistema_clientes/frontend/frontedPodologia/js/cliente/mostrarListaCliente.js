//import traerClientesFromDb from "./metodo/traerClientesFromDb.js";
import { traerClientesFromDb } from "./metodo/traerClientesFromDb.js";
import { comienzaProcesoDeleteClienteFromTotal } from "./metodo/comienzaProcesoDeleteClienteFromTotal.js";
const d = document;

export const mostrarListaClientes = (
  button_mostrar_clientes,
  ventana_listaTotal_clientes,
  button_eliminar_cliente_total
) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(button_mostrar_clientes)) {
      // alert("funciona el button de mostrar cliente");
      //aca se contruye un metodo que trae la

      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));

      const $ventana_total_clientes = d.querySelector(
        ventana_listaTotal_clientes
      );
      console.log(
        "el valor de la ventana total de clientes es: ",
        $ventana_total_clientes
      );

      if ($ventana_total_clientes)
        $ventana_total_clientes.classList.add("active");

      traerClientesFromDb();
    }
    if (e.target.matches(button_eliminar_cliente_total)) {
      //alert("funciona el buton de eliminar desde total");

      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new error("no se encontro fila");
        console.log("el valor de la fila es: ", fila);

        const idCliente = parseInt(fila.dataset.id, 10);

        if (isNaN(idCliente)) throw new Error("ID de cliente inválido");

        console.log("🔑 ID de cliente a eliminar:", idCliente); // ⬅️ verificación

        const eliminado = await comienzaProcesoDeleteClienteFromTotal(
          idCliente
        );

        if (eliminado?.success) {
          console.log("✅ Eliminando fila del DOM");
          fila.remove();
          alert("✅ Cliente eliminado de total correctamente");
        } else {
          alert("❌ No se pudo eliminar el cliente");
        }
      } catch (error) {
        console.error(
          "❌ Error obteniendo datos de cliente para eliminarlo del total:",
          error
        );
      }
    }
  });
};
