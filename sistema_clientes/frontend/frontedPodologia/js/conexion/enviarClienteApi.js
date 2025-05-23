const URL_API = "http://localhost:8080/cliente/crear";

export async function enviarClienteApi(cliente) {
  try {
    const respuesta = await fetch(URL_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(cliente),
    });

    if (!respuesta.ok) {
      throw new Error(`Error del servidor: ${respuesta.status}`);
    }

    const texto = await respuesta.text();
    console.log("✅ Cliente enviado correctamente:", texto);
    alert("✅ Cliente guardado exitosamente");
    return { exito: true, mensaje: texto };
  } catch (error) {
    console.error("❌ Error al enviar cliente:", error.message);
    alert("❌ Error al guardar cliente");
    return { error: true, mensaje: error.message };
  }
}
