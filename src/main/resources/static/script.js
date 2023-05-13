const cbtn = document.getElementById("closebutton");
const abtn = document.getElementById("addbutton");
const overlay = document.getElementById("new-zone");

abtn.addEventListener("click", () => {
  overlay.classList.add("show");
});

cbtn.addEventListener("click", () => {
  overlay.classList.remove("show");
});
