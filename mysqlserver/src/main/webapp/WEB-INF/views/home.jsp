<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MySQL Server</title>
</head>
<body>
	<form action="item/insert" 
		method="post" 
		enctype="multipart/form-data"
		id="itemform">
		이름:<input type="text" name="itemname"/>
		<br/>
		설명:<input type="text" name="description"/>
		<br/>
		가격:<input type="text" name="price"/>
		<br/>
		이미지:<input type="file" name='pictureurl' 
		accept="image/*"/>
		<br/>
		
		<input type="button" value="데이터 삽입"
		id="insertbtn" />
	</form>
</body>

<script>
	document.getElementById("insertbtn")
		.addEventListener('click', function(e){
			//폼에 입력된 데이터 가져오기
			var formData = new FormData(
				document.getElementById("itemform"));
			//ajax로 데이터를 전송해서 결과를 받기
			var xhr = new XMLHttpRequest();
			//요청 생성
			xhr.open("POST", "item/insert", true);
			//전송
			xhr.send(formData);
			//응답 받기
			xhr.onload = function(){
				if(xhr.status == 200){
					alert(xhr.responseText);
					var o = JSON.parse(xhr.responseText);
					if(o.result == true){
						location.href="item/list"
					}
				}
			}
	})
</script>

</html>


