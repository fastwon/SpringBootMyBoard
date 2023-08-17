var idDuplicatedChecked = false; // 중복확인 버튼이 눌렸는지 확인하는 상태 변수
var nameDuplicatedChecked = false;

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

    if (!idDuplicatedChecked) {
        alert("아이디 중복확인을 해주세요.");
        return false;
    }
	
	if(notEqualPw(pInput, pInput2)) {
		alert("비밀번호가 다릅니다.");
		return false;
	}
	
	if (!nameDuplicatedChecked) {
        alert("이름 중복확인을 해주세요.");
        return false;
    }
	
		return true;
}

function checkDuplicate() {
    var id = document.signUpForm.id.value;
    if (id) {
        var url = '/member/checkId?id=' + encodeURIComponent(id);
        var xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                if (xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);
	                if (response.isDuplicated) {
	                    alert("중복된 아이디입니다.");
	                } else {
	                    alert("사용 가능한 아이디입니다.");
                        idDuplicatedChecked = true; // 중복확인 상태를 true로 설정
	                }
                } else {
                    alert("서버에 문제가 발생했습니다. 다시 시도해주세요.");
                }
            }
        };

        xhr.open("GET", url, true);
        xhr.send(null);

    } else {
        alert("아이디를 입력해주세요.");
    }
}
        
function checkNameDuplicate() {
    var name = document.signUpForm.name.value;
    if (name) {
        var url = '/member/checkName?name=' + encodeURIComponent(name);
        var xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                if (xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);
	                if (response.nameDuplicated) {
	                    alert("중복된 이름입니다.");
	                } else {
	                    alert("사용 가능한 이름입니다.");
                        nameDuplicatedChecked = true; // 중복확인 상태를 true로 설정
	                }
                } else {
                    alert("서버에 문제가 발생했습니다. 다시 시도해주세요.");
                }
            }
        };

        xhr.open("GET", url, true);
        xhr.send(null);

    } else {
        alert("이름을 입력해주세요.");
    }
}

