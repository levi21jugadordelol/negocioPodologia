import { comienzoProcesoDeleteCliente } from "./metodo/comienzoProcesoDeleteCliente.js";
const d = document;

export const eventoEliminarCliente = (button_eliminar_cliente) => {
  d.addEventListener("click", async (e) => {
    if (e.target.matches(button_eliminar_cliente)) {
      //alert("funciona el button de eliminar");
      try {
        const fila = e.target.closest("tr");
        if (!fila) throw new Error("No se encontró la fila");
        console.log("el valor de fila es: ", fila);

        const idCliente = parseInt(fila.dataset.id, 10);

        if (isNaN(idCliente)) throw new Error("ID de cliente inválido");

        console.log("🔑 ID de cliente a eliminar:", idCliente); // ⬅️ verificación

        const eliminado = await comienzoProcesoDeleteCliente(idCliente);

        if (eliminado?.success) {
          console.log("✅ Eliminando fila del DOM");
          fila.remove(); // <--- esto elimina visualmente la fila
          alert("✅ Cliente eliminado correctamente");
        } else {
          alert("❌ No se pudo eliminar el cliente");
        }
      } catch (error) {
        console.error(
          "❌ Error obteniendo datos de cliente para eliminar:",
          error
        );
      }
    }
  });
};
