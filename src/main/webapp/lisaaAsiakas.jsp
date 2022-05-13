<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Lisää asiakas</title>
</head>
<body>
<form id="tiedot">
	<table>
		<thead>	
			<tr>
				<th colspan="5" class="oikealle"><span id="takaisin">Takaisin listaukseen</span></th>
			</tr>		
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Sposti</th>
				<th>Puhelin</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="etunimi" id="etunimi"></td>
				<td><input type="text" name="sukunimi" id="sukunimi"></td>
				<td><input type="text" name="sposti" id="sposti"></td>
				<td><input type="text" name="puhelin" id="puhelin"></td> 
				<td><input type="submit" id="tallenna" value="Lisää"></td>
			</tr>
		</tbody>
	</table>
</form>
<span id="ilmo"></span>
</body>
<script>
function tutkiKey(event){
	if(event.keyCode==13){
		lisaaTiedot();
	}
	
}

document.getElementById("asiakas_id").focus();



function lisaaTiedot(){	
	var ilmo="";
	var d = new Date();
	if(document.getElementById("asiakas_id").value.length<3){
		ilmo="Asiakastunnus ei kelpaa!";		
	}else if(document.getElementById("etunimi").value.length<2){
		ilmo="Etunimi ei kelpaa!";	
	}else if(document.getElementById("sukunimi").value.length<2){
		ilmo="Sukunimi ei kelpaa!";	}
	else if(document.getElementById("sposti").value.length<2){
			ilmo="Sposti ei kelpaa!";	
	}else if(document.getElementById("puhelin").value*1!=document.getElementById("puhelin").value){
		ilmo="Puhelin numero ei ole luku!";	
	}
	if(ilmo!=""){
		document.getElementById("ilmo").innerHTML=ilmo;
		setTimeout(function(){ document.getElementById("ilmo").innerHTML=""; }, 3000);
		return;
	}
	document.getElementById("etunimi").value=siivoa(document.getElementById("etunimi").value);
	document.getElementById("sukunimi").value=siivoa(document.getElementById("sukunimi").value);
	document.getElementById("sposti").value=siivoa(document.getElementById("sposti").value);
	document.getElementById("puhelin").value=siivoa(document.getElementById("puhelin").value);	
		
	var formJsonStr=formDataToJSON(document.getElementById("tiedot")); 
	fetch("asiakkaat",{
	      method: 'POST',
	      body:formJsonStr
	    })
	.then( function (response) {
		return response.json()
	})
	.then( function (responseJson) {
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Asiakkaan lisääminen epäonnistui";
      	}else if(vastaus==1){	        	
      		document.getElementById("ilmo").innerHTML= "Asiakkaan lisääminen onnistui";			      	
		}
		setTimeout(function(){ document.getElementById("ilmo").innerHTML=""; }, 5000);
	});	
	document.getElementById("tiedot").reset(); 
}
</script>
</html>