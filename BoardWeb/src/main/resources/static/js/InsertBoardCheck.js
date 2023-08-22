function checkValid() {
	let tInput = document.isbForm.title;
	
	if(atLeastLetter(tInput, 2)) {
		alert("제목은 두글자 이상이여야 합니다.");
		
		return false;
		
	}
	
	return true;
}