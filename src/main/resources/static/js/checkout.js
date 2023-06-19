function checkoutSteps(step) {
	console.log("AAAAAAAAAAA:" + step);
	const delDiv = document.querySelector('.active');
	delDiv.classList.remove('active');
	const addDiv = document.getElementById(step);
	addDiv.classList.add('active');


}

function populateIlceler(ilId) {

	var listsDiv = document.getElementById("lists");
	var districtListAttr = listsDiv.getAttribute("district-list");
	var districtList = JSON.parse(districtListAttr);
	var ilceSelect = document.getElementById("ilce");
	ilceSelect.innerHTML = '<option value="">Seçim Yapınız</option>';
	if (ilId !== "") {
		console.log(districtList[1])
		for (var i = 0; i < districtList.length; i++) {
			if (districtList[i].city.id == ilId) {
				var option = document.createElement("option");
				option.value = districtList[i].id;
				option.text = districtList[i].name;
				ilceSelect.appendChild(option);
			}
		}
	}
}

function populateMahalleler(ilceId) {
	var listsDiv = document.getElementById("lists");
	var neighborhoodListAttr = listsDiv.getAttribute("neighborhood-list");
	var neighborhoodList = JSON.parse(neighborhoodListAttr);
	var mahalleSelect = document.getElementById("mahalle");
	mahalleSelect.innerHTML = '<option value="">Seçim Yapınız</option>';
	if (ilceId !== "") {
		for (var i = 0; i < neighborhoodList.length; i++) {
			if (neighborhoodList[i].district.id == ilceId) {
				var option = document.createElement("option");
				option.value = neighborhoodList[i].id;
				option.text = neighborhoodList[i].name;
				mahalleSelect.appendChild(option);
			}
		}
	}
}

function orderCompleated(){
	window.location.href = "/orderCompleated";
}
