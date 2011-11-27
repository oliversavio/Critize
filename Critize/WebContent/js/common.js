/**
 * To show divs
 */

function showContent(divId){
	alert(12);
	var commentDiv =  document.getElementById(divId);
	
	if(commentDiv.style.display == "none"){
		document.getElementById(divId).style.display = "block";
		alert(1);
	}else{
		document.getElementById(divId).style.display = "none";
		alert(2);
	}
	
}