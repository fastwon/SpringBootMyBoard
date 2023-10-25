function checkValid() {
	let tInput = document.isbForm.title;
	
	if(atLeastLetter(tInput, 2)) {
		alert("제목은 두글자 이상이여야 합니다.");
		
		return false;
		
	}
	
	let category = document.isbForm.category.value;
	let mInput = document.isbForm.media;
	let yInput = document.isbForm.youtube;

	let vStart = document.isbForm.vStart.value;	
	let vEnd = document.isbForm.vEnd.value;	
	
	if(category == 'GAME') {
		if(!mInput.value) {
			alert("동영상(mp4)을 업로드하지 않았습니다.");
			
			return false;
		}
		
		if(vStart > (vEnd - 2)) {
			alert("동영상의 길이는 2초 이상이어야합니다.");
			return false;
		}
	}
	
	if(category == 'FOOTBALL') {
		if(!mInput.value) {
			alert("이미지를 업로드하지 않았습니다.");
			
			return false;
		}
	}
	
	if(category == 'MUSIC') {
		if(!yInput.value) {
			alert("유튜브 링크를 입력하지 않았습니다.");
			
			return false;
		}
	}
	
	if(category == "" && mInput.value) {
		if(mInput.files[0].type.startsWith('video')) {
			if(vStart > (vEnd - 2)) {
				alert("동영상의 길이는 2초 이상이어야합니다.");
				return false;
			}	
		}
	}
	
	return true;
}