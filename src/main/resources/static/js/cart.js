var userProductListElem = document.getElementById("user-product-list-div");
var userProductListJSON = userProductListElem.getAttribute("user-product-list");
var userProductList = JSON.parse(userProductListJSON);

async function addToCart(data) {
	var product = await JSON.parse(data.getAttribute("data-product"));
	// userProductList listesinde bu id'ye sahip ürün var mı kontrol edin
	var productIndex = await userProductList.findIndex(function(finded) {
		return finded.product.name == product.name;
	});
	if (productIndex == -1) {
		// bu id'ye sahip bir ürün yok, yeni bir ürün ekle
		var userProduct = await sendData({ status: 1, quantity: 1, product: product }, '/cart/updateCart');
		await userProductList.push(userProduct);
		console.log("Sended product: " + JSON.stringify(userProductList[userProductList.length - 1]));
	} else {
		// bu id'ye sahip bir ürün var, adet değerini arttır
		userProductList[productIndex].quantity++;
		await sendData(userProductList[productIndex], '/cart/updateCart');
	}
	refreshCart();
}


function removeToCart(productId) {
	// userProductList listesinde bu id'ye sahip ürünü bulun
	var hedefIndex = -1;
	userProductList.forEach(function(userProduct, index) {
		if (userProduct.product.id == productId) {
			hedefIndex = index;
			return;
		}
	});
	if (hedefIndex != -1) {
		if (userProductList[hedefIndex].quantity > 1) {
			userProductList[hedefIndex].quantity = userProductList[hedefIndex].quantity - 1;
			sendData(userProductList[hedefIndex], '/cart/updateCart');
		}
		else {
			sendData(userProductList[hedefIndex].id, '/cart/removeFromCart');
			userProductList.splice(hedefIndex, 1);
		}

	}
	refreshCart();
}
function refreshCart() {
	const productList = document.querySelector("#product-list-ul");
	productList.innerHTML = ""; // Eski ürünleri temizle
	userProductList.forEach((userProduct) => {
		// Yeni ürünleri ekle
		const li = document.createElement("li");
		li.id = `userProduct-${userProduct.product.id}`;
		li.innerHTML = `<div class="minicart-item">
                      <div class="thumb">
                          <a href="#"><img src="/images/products/${userProduct.product.imgPath}" width="90" height="90" alt="National Fresh"></a>
                      </div>
                      <div class="left-info">
                          <div class="product-title"><a href="#" class="product-name">${userProduct.product.name}</a></div>
                          <div class="price">
                              <ins><span class="price-amount"><span class="currencySymbol"></span>${userProduct.product.price}</span></ins>
                              <del><span class="price-amount"><span class="currencySymbol"></span>${userProduct.product.price}</span></del>
                          </div>
                          <div class="qty">
                              <label >Adet: </label>
                              <input type="number" class="input-qty" value="${userProduct.quantity}" disabled>
                          </div>
                      </div>
                      <div class="action">
                          <a class="edit"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                          <a onclick="removeToCart(${userProduct.product.id})" class="remove"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                      </div>
                    </div>`;
		productList.appendChild(li);
	});
}
async function sendData(productData, endpoint) {
	console.log("SendData- " + "data: " + JSON.stringify(productData) + " endpoint: " + endpoint);
	return await $.ajax({
		url: endpoint,
		type: 'POST',
		data: JSON.stringify(productData),
		contentType: 'application/json',
		success: function(response) {
			console.log("sendData() response: " + response);
		},
		error: function(sendData, status, error) {
			console.error('sendData() istek hatası:', error);
		}
	});
}