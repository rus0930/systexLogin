/*const login = () => {
	const myHeaders = new Headers();
	myHeaders.append("Content-Type", "application/json");
	fetch("/api/login", {
		method: "POST",
		body: JSON.stringify({
			acc: document.getElementsByName("acc")[0].value,
			pas: document.getElementsByName("pas")[0].value
		}),
		headers: myHeaders
	}).then(response => {
		if(response.status===200){
			document.getElementById('status').innerText = "登入成功"
		}else{
			document.getElementById('status').innerText = response.status===401 ? "帳號或密碼錯誤" :"server error"
		}
	}).catch(err => {
		console.log(err)
		document.getElementById('status').innerText = "internet error"
	})
}*/

const login = () => {
	$.ajax({
		url: "/api/login",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			acc: $("input[name='acc']").val(),
			pas: $("input[name='pas']").val()
		}),
		success: function(data, textStatus, jqXHR) {
			let i = 3;
			for (let j = 0; j < 3; j++) {
				setTimeout(() => {
					$("#status").text(`登入成功 ${i--}秒後跳轉至首頁`);
				}, j * 1000)
			}
			setTimeout(() => {
				window.location.replace("/home");
			}, 3000)
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$("#status").text(jqXHR.status === 401 ? "帳號或密碼錯誤" : "server error or internet");
		}
	});
};

