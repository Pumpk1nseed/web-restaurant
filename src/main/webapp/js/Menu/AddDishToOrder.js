var quantityInp;

function addDishToOrder(el, idCategory, idDish) {
    quantityInp = document.querySelector(`#quantityOf${idDish}_${idCategory}`);
    const url = "http://localhost:8080/web_restaurant_war/ajaxController";
    const body = `command=add_dish_to_order&idDish=${idDish}&quantity=${quantityInp.value}`;

    sendRequest(url, 'POST', body)
        .then((resp) => { onDishAdded(resp, el) })
}

function onDishAdded(resp, addDishToOrderBtn) {
        let addedSpan = document.querySelector('#dishAddedMsg');
        let td4 = addDishToOrderBtn.parentElement;

        let quantityOfDishesSpan = document.querySelector('#quantityDish')

        if (addedSpan != addDishToOrderBtn.nextElementSibling) {
            addedSpan.remove()

            addedSpan = document.createElement('span');
            addedSpan.id = "dishAddedMsg"

            td4.appendChild(addedSpan);
        }
        if (addDishToOrderBtn.nextElementSibling == null) {
            addedSpan = document.createElement('span');
            addedSpan.id = "dishAddedMsg"

            td4.appendChild(addedSpan);
        }
}