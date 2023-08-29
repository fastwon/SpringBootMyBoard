//package com.fastwon.board.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SessionController {
//
//	@GetMapping("/api/session/remaining-time")
//	public long getRemainingSessionTime(HttpServletRequest request) {
//	    HttpSession session = request.getSession(false);
//
//	    if (session != null && !session.isNew()) {
//	        long currentTime = System.currentTimeMillis();
//	        long lastAccessedTime = session.getLastAccessedTime();
//	        int maxInactiveInterval = session.getMaxInactiveInterval();
//
//	        long timeSinceLastAccessed = currentTime - lastAccessedTime;
//	        return maxInactiveInterval * 1000 - timeSinceLastAccessed; // 밀리초 단위로 계산
//	    }
//
//	    return 0;
//	}
//}
//
//
