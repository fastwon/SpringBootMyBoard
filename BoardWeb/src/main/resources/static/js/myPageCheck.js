function checkValid(){
	let pInput0 = document.mpForm.password;
	
	let pInput = document.mpForm.passwordSet;
	let pInput2 = document.mpForm.passwordChk;
	
	if (isEmpty(pInput) || isEmpty(pInput0)) {
		alert("비밀번호를 입력하지 않았습니다.");
		return false;
	}
	
	
	if(atLeastLetter(pInput, 8)) {
		alert("비밀번호는 8글자 이상이어야 합니다.")
		return false;
	}
	
	if(notEqualPw(pInput, pInput2)) {
		alert("비밀번호가 다릅니다.");
		return false;
	}
	
		return true;
}

function chkForDelete() {
	let pInput = document.mdForm.password;
	
	if(isEmpty(pInput)) {
		alert("비밀번호를 입력하지 않았습니다.");
		return false;
	}
	
	return true;
	
}