
//Filtreleme
function filterItems(className) {
	var items = document.querySelectorAll(".anaUrun"); // className parametresini kullanarak elemanları seç
	var filteredItems = document.querySelectorAll("." + className); // className parametresini kullanarak elemanları seç
	for (var i = 0; i < items.length; i++) {
		items[i].style.display = "none"; // elemanların görünürlüğünü "none" olarak ayarla
	}
	for (var i = 0; i < filteredItems.length; i++) {
		filteredItems[i].style.display = "inline-block"; // elemanların görünürlüğünü "none" olarak ayarla
	}
}

function showItems() {
	var items = document.querySelectorAll(".anaUrun"); // className parametresini kullanarak elemanları seç
	for (var i = 0; i < items.length; i++) {
		items[i].style.display = "inline-block"; // elemanların görünürlüğünü "none" olarak ayarla
	}
}