document.addEventListener('DOMContentLoaded', initSearch);

function initSearch() {

    console.log("initSearch")

    const resetBtn = document.getElementById('resetBtn');
    if (!resetBtn) return;

    const form = resetBtn.closest('form');
    if (!form) return;

    resetBtn.addEventListener('click', handleResetClick);
}

function handleResetClick(event) {
    const form = event.currentTarget.closest('form');
    if (!form) return;

    const input = form.querySelector('#contient');
    const select = form.querySelector('#categorie');

    if (input) input.value = '';
    if (select) select.selectedIndex = 0;

    form.submit();
}