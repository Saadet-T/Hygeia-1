var userProductListElem = document.getElementById("user-product-list-div");
var userProductListJSON = userProductListElem.getAttribute("user-product-list");
var userProductList = JSON.parse(userProductListJSON);

function addToCart(data) {
	var product = JSON.parse(data.getAttribute("data-product"));
	// userProductList listesinde bu id'ye sahip ürün var mı kontrol edin
	var productIndex = userProductList.findIndex(function(finded) {
		return finded.product.id == product.id;
	});
	if (productIndex === -1) {
		// bu id'ye sahip bir ürün yok, yeni bir ürün ekle
		userProductList.push({ status: 1, quantity: 1, product: product });
		sendData(userProductList[userProductList.length - 1],'/cart/addToCart');
		console.log("Sended product: "+JSON.stringify(userProductList[userProductList.length - 1]));
	} else {
		// bu id'ye sahip bir ürün var, adet değerini arttır
		userProductList[productIndex].quantity++;
		sendData(userProductList[productIndex],'/cart/addToCart');
	}
	refreshCart();
}


function removeToCart(jsonProduct) {
	var product = JSON.parse(jsonProduct);
	// userProductList listesinde bu id'ye sahip ürünü bulun
	var productIndex = userProductList.findIndex(function(finded) {
		return finded.id == product.id;
	});

	if (productIndex !== -1) {
		// bu id'ye sahip bir ürün var, listeden çıkar
		userProductList.splice(productIndex, 1);
	}
	refreshCart();
}
function refreshCart() {
	const productList = document.querySelector("#product-list-ul");
	console.log(productList);
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
                              <ins><span class="price-amount"><span class="currencySymbol">£</span>${userProduct.product.price}</span></ins>
                              <del><span class="price-amount"><span class="currencySymbol">£</span>${userProduct.product.price}</span></del>
                          </div>
                          <div class="qty">
                              <label >Adet: </label>
                              <input type="number" class="input-qty" value="${userProduct.quantity}" disabled>
                          </div>
                      </div>
                      <div class="action">
                          <a class="edit"><i class="fa fa-pencil" aria-hidden="true"></i></a>
                          <a class="remove"><i class="fa fa-trash-o" aria-hidden="true"></i></a>
                      </div>
                    </div>`;
		productList.appendChild(li);
	});
}
function sendData(data,endpoint) {
	console.log("SendData:"+data+endpoint);
	const xhr = new XMLHttpRequest();
	xhr.open('POST', endpoint);
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {
//				const resultDiv = document.querySelector('#result');
//				resultDiv.innerHTML = response.message;
//								console.log("RESPONS: "+xhr.responseText);
			} else {
				console.error('Error! Sendding Cart data to backend');
			}
//			hideLoading();
		} else {
//			showLoading();
		}
	};
	xhr.send(JSON.stringify(data));
}