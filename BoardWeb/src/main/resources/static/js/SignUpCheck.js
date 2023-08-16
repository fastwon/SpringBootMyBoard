var isDuplicatedChecked = false; // 중복확인 버튼이 눌렸는지 확인하는 상태 변수

function checkValid(){
	let idInput = document.signUpForm.id;
	let pInput = document.signUpForm.password;
	let pInput2 = document.signUpForm.passwordChk;
	let nInput = document.signUpForm.name;
	
	if (isEmpty(pInput) || isEmpty(idInput) || isEmpty(nInput)) {
		alert("아이디 또는 비밀번호 또는 이름을 입력하지 않았습니다.");
		return false;
	}
	
	if (containAnother(idInput)) {
		alert("아이디는 \n영어 숫자 ! @ _ . \n로만 작성 가능합니다.");
		return false;
	}

    if (!isDuplicatedChecked) {
        alert("중복확인을 먼저 해주세요.");
        return false;
    }
	
	if(notEqualPw(pInput, pInput2)) {
		alert("비밀번호가 다릅니다.");
		return false;
	}
	
		return true;
}

