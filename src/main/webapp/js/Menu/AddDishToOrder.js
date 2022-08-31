var quantityInp;

function addDishToOrder(el, idCategory, idDish) {
    quantityInp = document.querySelector(`#quantityOf${idDish}_${idCategory}`);
    const url = "http://localhost:8080/web_restaurant_war/ajaxController";
    const body = `command=add_dish_to_order&idDish=${idDish}&quantity=${quantityInp.value}`;

    sendRequest(url, 'POST', body)
        .then((response) => { onDishAdded(response, el) })
        .catch((msg) => { alert(msg) })
}

function onDishAdded(response, addDishToOrderBtn) {
    if (response.validationError) {
        alert(response.message)
    } else {
        let addedSpan = document.querySelector('#dishAddedMsg');
        let td4 = addDishToOrderBtn.parentElement;

        let quantityOfDishesSpan = document.querySelector('#quantityDish')
        let oldCount = quantityOfDishesSpan.innerHTML
        quantityOfDishesSpan.innerHTML = Number(oldCount) + Number(quantityInp.value)

        if (addedSpan != addDishToOrderBtn.nextElementSibling) {
            addedSpan.remove()

            addedSpan = document.createElement('span');
            addedSpan.id = "dishAddedMsg"
            addedSpan.innerHTML = "<br> <div style=\"color: rgb(27, 167, 27);\">&#10004</div>";

            td4.appendChild(addedSpan);
        } else if (addDishToOrderBtn.nextElementSibling == null) {
            addedSpan = document.createElement('span');
            addedSpan.id = "dishAddedMsg"
            addedSpan.innerHTML = "<br> <div style=\"color: rgb(27, 167, 27);\">&#10004</div>";

            td4.appendChild(addedSpan);
        }
    }
}