function checkValid(){
	let idInput = document.signUpForm.id;
	let pInput = document.signUpForm.password;
	let pInput2 = document.signUpForm.passwordChk;

	if (isEmpty(pInput) || isEmpty(idInput)) {
		alert("잘못 입력!");
		return false;
	} else if(notEqualPw(pInput, pInput2)) {
		alert("비밀번호가 다릅니다.")
		return false;
	} else {
		return true;
	}
}