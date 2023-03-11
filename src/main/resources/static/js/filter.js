//Filtreleme
function filterItems(className) {
	showAllColumns()
	var items = document.querySelectorAll(".anaUrun");
	var filteredItems = document.querySelectorAll("." + className);
	for (var i = 0; i < items.length; i++) {
		items[i].parentElement.style.display = "none";
	}
	for (var i = 0; i < filteredItems.length; i++) {
		filteredItems[i].parentElement.style.display = "inline-block";
	}
	hideEmptyColumns();
}

function showItems() {
	var items = document.querySelectorAll(".anaUrun");
	showAllColumns();
	for (var i = 0; i < items.length; i++) {
		items[i].parentElement.style.display = "inline-block";
	}
}

function hideEmptyColumns() {
	var items = document.querySelectorAll(".slick-slide");
	for (var i = 0; i < items.length; i++) {
		var hasElement = false;
		var altElementler = items[i].children;
		for (var j = 0; j < altElementler.length; j++) {
			if (window.getComputedStyle(altElementler[j]).display != "none") {
				hasElement = true;
			}
		}
		if(!hasElement){
			items[i].style.display = "none";
		}
		
	}
}
function showAllColumns() {
	var items = document.querySelectorAll(".slick-slide");
	for (var i = 0; i < items.length; i++) {
		items[i].style.display = "block";
	}
}