let initialized = false;
function init2() {

    console.log("init js");
    if (initialized) {
        console.warn("init() déjà appelé, on skip");
        return;
    }

    initialized = true;

    document.getElementById('resetBtn').addEventListener('click', function() {
        // Vide les champs du formulaire
        const categorieSelect = document.querySelector('select[name="categorieId"]');
        const contientInput = document.querySelector('input[name="contient"]');

        if (categorieSelect) categorieSelect.value = '';
        if (contientInput) contientInput.value = '';

        // Soumet le formulaire (ou redirige vers la page sans params)
        // Si ton formulaire a un id, mieux vaut utiliser submit()
        document.querySelector('form.filterForm').submit();
    });


}

function init() {
    console.log("init js");
    if (initialized) {
        console.warn("init() déjà appelé, on skip");
        return;
    }
    initialized = true;

    console.log("init js lancé UNE fois");
    const form = document.getElementById('filterForm');
    const resetBtn = document.getElementById('resetBtn');
    const resetBtnImg = document.getElementById('resetBtnImg');
    const champContient = document.getElementById('contient');
    const champCategorie = document.getElementById('categorie');


    console.log("resetBtn", resetBtn);
    // Affiche ou cache le bouton reset selon l'état des champs
    function checkFilters() {
        const contientHasValue = champContient && champContient.value.trim() !== '';
        const categorieHasValue = champCategorie && champCategorie.value.trim() !== '';

        console.log("checkFilters → contient:", champContient.value, "| categorie:", champCategorie.value);

        if (contientHasValue || categorieHasValue) {
            resetBtnImg.classList.remove('hidden');
            resetBtn.classList.remove('hidden');
            console.log("remove hidden");
        } else {
            resetBtnImg.classList.add('hidden');
            resetBtn.classList.add('hidden');
            console.log("add hidden")
        }
    }

    // À l'initialisation : vérifier l'état des champs
    checkFilters();

    // Écouteurs pour mise à jour en temps réel
    champContient?.addEventListener('input', checkFilters);
    champCategorie?.addEventListener('change', checkFilters);

    // Clic sur le bouton reset = redirection propre
    resetBtn.addEventListener('click', function () {
        const typeEnchere = document.getElementById('typeEnchere')?.value || '0';

        // On recharge la page avec uniquement typeEnchere
        window.location.href = `${window.location.pathname}?typeEnchere=${typeEnchere}`;
        // console.log("click on reset");
        //
        // const baseUrl = window.location.origin + window.location.pathname;
        // window.location.href = baseUrl; // recharge sans les paramètres
    });


    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const typeEnchere = document.getElementById('typeEnchere')?.value;
        const url = new URL(window.location.href);
        const formData = new FormData(form);
        const params = new URLSearchParams(formData);

        if (typeEnchere) {
            params.set('typeEnchere', typeEnchere);
        }

        // Redirige avec les bons paramètres
        window.location.href = window.location.pathname + '?' + params.toString();
    });
}

document.addEventListener('DOMContentLoaded', init2);