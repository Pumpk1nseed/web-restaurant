let count;

function addOne(event, idCategory, idDish) {
    event.preventDefault();

    let inputQuantity = document.querySelector(`#quantityOf${idDish}_${idCategory}`);

    count = +inputQuantity.value;

    inputQuantity.value = ++count;
}

function reduceOne(event, idCategory, idDish) {
    event.preventDefault();

    let inputQuantity = document.querySelector(`#quantityOf${idDish}_${idCategory}`);

    count = +inputQuantity.value;

    if (--count < 1) {
        count = 1;
    }
    inputQuantity.value = count;
}

function validate(evt) {
    var theEvent = evt || window.event;
    var key = theEvent.keyCode || theEvent.which;
    key = String.fromCharCode(key);
    var regex = /^[\d]+$/

    if (!regex.test(key)) {
        theEvent.returnValue = false;
        if (theEvent.preventDefault) theEvent.preventDefault();
    }
}