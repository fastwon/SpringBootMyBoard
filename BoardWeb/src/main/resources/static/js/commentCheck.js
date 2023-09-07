function checkValid() {
	let cInput = document.cmtForm.cmtContent;
	
	if(atLeastLetter(cInput, 2)) {
		alert("댓글은 두글자 이상이여야 합니다.");
		
		return false;
		
	}
	
	return true;
}