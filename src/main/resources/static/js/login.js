async function sendData(username,password, endpoint) {
	console.log("SendData- " + "login() " + " endpoint: " + endpoint);
	return await $.ajax({
		url: endpoint,
		type: 'POST',
		data: {username:username,password:password},
		success: function(response) {
			console.log("sendData() response: " + response);
		},
		error: function(sendData, status, error) {
			console.error('sendData() istek hatası:', error);
		}
	});
}

async function login() {
	var inputPassword = document.getElementById("password");
	var password = inputPassword.value;
	
	var inputUsername = document.getElementById("username");
	var username = inputUsername.value;
	
	var isSuccess = await sendData(username,password,"/api/auth/signin");
	if(isSuccess){
		window.location.href = "/";
	}
	else{
		alert("Kullanıcı Adı veya Şifre Yanlış..");
	}

}