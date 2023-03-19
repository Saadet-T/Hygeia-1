//Filtreleme


//YAPILACAK KODDA ESKİYE DÖNÜLECEK YANİ İLK FRONTENDDEKİ GİBİ TAB SİSTEMİ OLACAK 1.Tabda bütün elemanlar listelenecek Diğer Tablarda Kategori Bazlı Listeleme Olacak
function filterItems(className) {
	
	var items = document.querySelectorAll(".anaUrun");
	const filteredItems = document.querySelectorAll("." + className);
	showItems();
	for (var i = 0; i < items.length; i++) {
		 if(!isHasElement(filteredItems,items[i])){
			items[i].parentElement.style.display = "none";
		}
		
		
	}
	hideEmptyColumns();
}

function showItems() {
	var items = document.querySelectorAll(".anaUrun");
	
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
			console.log(items[i]);
			items[i].style.height = "0";
			items[i].style.width = "0";
		}
		
	}
}
function showAllColumns() {
	var items = document.querySelectorAll(".slick-slide");
	for (var i = 0; i < items.length; i++) {
		items[i].style.display = "block";
	}

}
function isHasElement(array,element){
	var key = false;
	for (var i = 0; i < array.length; i++) {
		if(array[i]==element)
		{
			key =true;
		}
	}
	return key;
}
