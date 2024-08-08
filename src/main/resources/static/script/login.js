/*const login = () => {
	const myHeaders = new Headers()
	myHeaders.append("Content-Type", "application/json")
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
	const status1=$("#status1")

	$.ajax({
		url: "/api/login",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			acc: $("input[name='acc']").val(),
			pas: $("input[name='pas']").val()
		}),
		success: function(data, textStatus, jqXHR) {
			status1.removeClass("bg-danger text-white")
			status1.addClass("text-success")
			for (let j = 0 ;j < 3 ;j++) {
				setTimeout(() => {
					status1.text(`登入成功 ${3-j}秒後跳轉至首頁`)
				}, j * 1000)
			}
			setTimeout(() => {
				window.location.replace("/home")
			}, 3000)
		},
		error: function(jqXHR, textStatus, errorThrown) {
			status1.text(jqXHR.status === 401 ? "帳號或密碼錯誤" : "server error or internet")
			status1.addClass("bg-danger text-white")
		}
	})
}

const signUp=() => {
	const status2=$("#status2")

	$.ajax({
		url: "/api/signUp",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			acc: $("#account").val(),
			pas: $("#password").val(),
			name:$("#nickname").val()
		}),
		success: function(data, textStatus, jqXHR) {
			status2.removeClass("bg-danger")
			status2.addClass("bg-success")
			status2.text(data?.message ?? "新增帳號成功")
		},
		error: function(jqXHR, textStatus, errorThrown) {
			status2.text(jqXHR.status === 500 ? "server error or internet" : jqXHR?.responseJSON?.message ?? "未知錯誤")
			status2.removeClass("bg-success")
			status2.addClass("bg-danger")
		}
	})
}

const switchView=()=>{
	const status1=$("#status1")
	const status2=$("#status2")
	
	$("#login").toggleClass("visually-hidden")
	$("#signUp").toggleClass("visually-hidden")
	status1.removeClass("bg-danger")
	status2.removeClass("bg-danger bg-success")
	status1.text(".")
	status2.text(".")
}


