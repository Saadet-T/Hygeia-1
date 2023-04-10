                
function sendUsernameToBackend() {
    const cookies = document.cookie.split("; ");
    let JWTcookie = "";
    let payload = "";
    cookies.forEach(cookie => {
      const [name, value] = cookie.split("=");
      if (name === "JWT") {
        JWTcookie += value;
        const chunks = JWTcookie.split(".");
        payload += decodeURIComponent(atob(chunks[1]).split("").map(function(c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(""));
      }
    }); 
    const start = payload.indexOf("{\"sub\":\"") + 8;
    const end = payload.indexOf("\",", start);
    const username = payload.substring(start, end);
    console.log("aaaaaa");
    const xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/getinfo");
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
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
	xhr.send(`username=${username}`);

}
