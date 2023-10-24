// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("openModal");

var span = document.getElementById("closeModal");

var undo = document.getElementById("undoModal");


// When the user clicks on the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// 모달창 확인버튼
span.onclick = function() {
    modal.style.display = "none";
    var previewVideo1= document.getElementById("videoPreview");
	var previewVideo2= document.getElementById("videoPreview2");

    // 이미지 미리보기가 있는 경우, 이미지 복사
    if(document.getElementById('imagePreview') != null && document.getElementById('imagePreview').src !== "#") {
        document.getElementById('imagePreview2').src = document.getElementById('imagePreview').src;
    }
	
	// 비디오 미리보기가 있는 경우, 비디오 복사
	if(previewVideo1.childNodes.length > 0) {
		var sourceNode= previewVideo1.childNodes[0].cloneNode(true);
		previewVideo2.innerHTML= "";
		previewVideo2.appendChild(sourceNode);
		previewVideo2.load();
	}
}

// 모달창 취소버튼
undo.onclick = function() {
	if(document.getElementById('imagePreview') != null) {
		document.getElementById('imagePreview').src = "#";
		document.getElementById('imagePreview2').src = "#";
	}
	
	var previewVideo1= document.getElementById("videoPreview");
	var previewVideo2= document.getElementById("videoPreview2");
	
    var fileInputField=  document.querySelector("input[type='file']");
    fileInputField.value= "";
	
    previewVideo1.innerHTML="";
    previewVideo1.load();
    previewVideo2.innerHTML="";
	previewVideo2.load();
    modal.style.display = "none";
}


/*//모달창 확인버튼
span.onclick = function() {
    modal.style.display = "none";
    document.getElementById('imagePreview2').src = document.getElementById('imagePreview').src;
}

// 모달창 취소버튼
undo.onclick = function() {
		document.getElementById('imagePreview').src = "#";
		document.getElementById('imagePreview2').src = document.getElementById('imagePreview2').src;
		 var fileInputField=  document.querySelector("input[type='file']");
		    fileInputField.value= "";
		modal.style.display = "none";
}*/

// When the user clicks anywhere outside of the modal, close it
/*window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}*/
