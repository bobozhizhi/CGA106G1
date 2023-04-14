const cartBody = document.querySelector("#cartbody")
cartBody.innerHTML = '';
let n = 1;
for(item of shoppingcart){
    const qty = item.qty;
    const pdNo = item.pdNo
    fetch(`/product/find-one?id=${pdNo}`,{
        method:"GET"
        }).then(response => response.json()
        ).then(data=>{
        cartBody.innerHTML += `<tr>
                    <td id = pdNo style = "display:none">${pdNo}</td>
                    <td id = pdStock style = "display:none">${data.pdStock}</td>
                    <td class="pro-thumbnail"><a href="product-details.html?pdNo=${pdNo}"><img class="img-fluid" src="static/picture/14.jpg" alt="Product"></a></td>
                    <td class="pro-title fs-5"><a href="product-details.html?pdNo=${pdNo}">${data.pdName}</td>
                    <td class="pro-price fs-5" ><span>$${data.pdPrice}</span></td>
                    <td class="pro-quantity">
                        <div class="quantity">
                            <div class="cart-plus-minus">
                                <input id = "qty${n}" class="cart-plus-minus-box" value="${qty}" type="text">
                                <div class="dec qtybutton">-</div>
                                <div class="inc qtybutton">+</div>
                                <div class="dec qtybutton"><i class="fa fa-minus"></i></div>
                                <div class="inc qtybutton"><i class="fa fa-plus"></i></div>
                            </div>
                        </div>
                    </td>
                    <td id = "totalAmount${n++}" class="pro-subtotal fs-5"><span>$${qty*data.pdPrice}</span></td>
                    <td class="pro-remove"><a href="javascript:void(0)"><i class="ion-trash-b"></i></a>
                    </td>
                </tr>`;

        $('.cart-plus-minus').append(
            '<div class="dec qtybutton"><i class="fa fa-minus"></i></div><div class="inc qtybutton"><i class="fa fa-plus"></i></div>'
        );
        $('.qtybutton').on('click', function () {
            let $button = $(this);
            let oldValue = $button.parent().find('input').val();
            let pdNo = +$button.closest("tr").find("#pdNo").text();
            let pdStock = +$button.closest("tr").find("#pdStock").text();
            if ($button.hasClass('inc')) {
                if(oldValue < +pdStock ){
                    var newVal = parseFloat(oldValue) + 1;
                } else {
                    var newVal = parseFloat(oldValue);
                }
            } else {
                // Don't allow decrementing below zero
                if (oldValue > 1) {
                    var newVal = parseFloat(oldValue) - 1;
                } else{
                    newVal = 1;
                }
            }
            $button.parent().find('input').val(newVal);
            
            set(pdNo,newVal);

            var unitPrice = +$button.closest("tr").find(".pro-price span").text().replace("$", "");
            $button.closest('tr').find(".pro-subtotal span").text("$" + (unitPrice * newVal))
        });

        const deleteButtons = document.querySelectorAll('.pro-remove');
        
        deleteButtons.forEach(button => {
            button.addEventListener('click', () => {
                const pdNoNode = button.closest('tr').querySelector('#pdNo');
                del(pdNoNode.textContent);
                const productRow = button.parentNode;
                productRow.remove();
            });
        });
    });
}

async function getProStock(proNo){
    let pdStock = 0;
    await fetch(`/product/find-one?id=${proNo}`)
    .then(response => response.json())
    .then(data =>{
        pdStock = data.pdStock;
    })
    return pdStock
}