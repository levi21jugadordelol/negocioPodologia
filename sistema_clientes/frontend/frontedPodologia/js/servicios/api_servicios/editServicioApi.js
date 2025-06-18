import { BASE_URL } from "../../config/configuracion.js";
import { servicioStorage } from "../../localStorage/servicioStorage.js";

const d = document;

export const editServicioApi = async (idServicio, servicioBackend) => {
  const URL_API = `${BASE_URL}/servicio/editar/${idServicio}`;

  try {
    const respuesta = await fetch(URL_API, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionStorage.token}`,
      },
      body: JSON.stringify(servicioBackend),
    });

    console.log("el servicio backend es: ", servicioBackend);

    if (!respuesta.ok) {
      throw new Error("No se pudo editar el servicio del backend");
    }

    const servicioFetch = await respuesta.json();

    console.log(`objeto editado : ${JSON.stringify(servicioFetch)}`);

    alert("✅ servicio editado exitosamente");
    const { idServicio } = servicioFetch;

    const $formularioServicioEdit = d.getElementById("form_servicio_edit");

    console.log(
      "el valor del formulario en el fetch de edit api es: ",
      $formularioServicioEdit
    );

    if ($formularioServicioEdit) {
      $formularioServicioEdit.dataset.id = idServicio;
    }

    servicioBackend.idServicio = idServicio;

    const servicioNormalizado = {
      nombreServicio: servicioBackend.nombre || servicioBackend.nombreServicio,

      precioServicio: servicioBackend.precio || servicioBackend.precioServicio,

      descripcionServicio:
        servicioBackend.descripcion || servicioBackend.descripcionServicio,

      duracioServicio:
        servicioBackend.duracion || servicioBackend.duracioServicio,
    };

    console.log(
      "servicio normalizado que se va a guardar: ",
      servicioNormalizado
    );

    // 1. Guardamos servicio
    servicioStorage.guardarServicio(servicioNormalizado);

    // 2. Obtenemos todos los servicios actualizados
    const todosServicios = servicioStorage.obtenerServicios();
    console.log("servicios actualizados en el ls", todosServicios);

    // Opcional: verificar si el producto editado está ahí
    const servicioEncontrado = todosServicios.find(
      (c) => c.idServicio === idServicio
    );

    if (servicioEncontrado) {
      console.log(
        "servicio editado encontrado en localStorage: ",
        servicioEncontrado
      );
    } else {
      console.warn("❌ servicio editado NO encontrado en localStorage.");
    }
    alert("✅ servicio editado guardado exitosamente");

    return idServicio;
  } catch (error) {
    console.error("❌ Error al editar del backend:", error.message);
    return false;
  }
};
