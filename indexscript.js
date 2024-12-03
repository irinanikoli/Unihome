//Θέλω την μορφοποίηση για το κείμενο
const minima = document.getElementById("minima");
const text = minima.textContent;
minima.textContent = "";


function typeWriter(i = 0) {
    if (i < text.length) {
        minima.textContent += text.charAt(i);
        setTimeout(() => typeWriter(i + 1), 120);
    } else {
        minima.style.visibility = "visible";
    }
}

window.onload = () => {
    typeWriter();
};

//Θέλω το κουμπί
const pame = document.querySelector('.pame');
pame.addEventListener('click', () => {
    window.location.href = "index2.html";

});
