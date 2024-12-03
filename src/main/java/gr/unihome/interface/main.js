// Η μπάρα για την τιμή
function updatePriceRange() {
    const minSlider = document.getElementById("price-min-slider");
    const maxSlider = document.getElementById("price-max-slider");
    const minValueDisplay = document.getElementById("price-min");
    const maxValueDisplay = document.getElementById("price-max");

    let minValue = parseInt(minSlider.value);
    let maxValue = parseInt(maxSlider.value);

    // Η μικρότερη τιμή δεν πρέπει να ξεπερνάει μεγαλύτερη τιμή
    if (minValue >= maxValue) {
        minValue = maxValue - 10;
        minSlider.value = minValue;
    }

    if (maxValue <= minValue) {
        maxValue = minValue + 10;
        maxSlider.value = maxValue;
    }

    minValueDisplay.textContent = `€${minValue}`;
    maxValueDisplay.textContent = `€${maxValue}`;
}
 
// Ναι ή οχι
document.getElementById("yes-no").addEventListener("change", function () {
    const selectedOption = document.querySelector('input[name="agreement"]:checked').value;
    console.log("Selected option:", selectedOption); // Καταγράφει την επιλογή του χρήστη   
});

document.addEventListener("DOMContentLoaded", function() {
    const minInput = document.querySelector("#min");
    const maxInput = document.querySelector("#max");

    minInput.addEventListener("input", validateRange);
    maxInput.addEventListener("input", validateRange);

    function validateRange() {
        const min = parseFloat(minInput.value);
        const max = parseFloat(maxInput.value);

        if (min && max && min > max) {
            // Άμα το min είναι μεγαλύτερο του max, βγάζει alert
            alert("Τα ελάχιστα τετραγωνικά μέτρα δεν μπορούν να είναι μεγαλύτερα από το μέγιστο.");
            minInput.value = "";
            maxInput.value = "";
        }
    }
});

