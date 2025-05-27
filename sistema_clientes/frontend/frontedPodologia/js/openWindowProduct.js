const d = document;
export const openWindowProduct = (button_open_product, div_producto) => {
  d.addEventListener("click", (e) => {
    if (
      e.target.matches(button_open_product) ||
      e.target.closest(button_open_product)
    ) {
      //alert("funciona el buton de producto");
      const allSections = d.querySelectorAll(".section");
      allSections.forEach((sec) => sec.classList.remove("active"));
      const $sectionProducto = d.querySelector(div_producto);
      if ($sectionProducto) $sectionProducto.classList.add("active");
    }
  });
};
