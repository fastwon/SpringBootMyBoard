// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("openModal");

// Get the element that closes the modal
var span = document.getElementById("closeModal");

var undo = document.getElementById("undoModal");

// When the user clicks on the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    document.getElementById('imagePreview2').src = document.getElementById('imagePreview').src;
}

// When the user clicks anywhere outside of the modal, close it
/*window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}*/

undo.onclick = function() {
		document.getElementById('imagePreview').src = "#";
		document.getElementById('imagePreview2').src = "#";
		 var fileInputField=  document.querySelector("input[type='file']");
		    fileInputField.value= "";
		modal.style.display = "none";
}

