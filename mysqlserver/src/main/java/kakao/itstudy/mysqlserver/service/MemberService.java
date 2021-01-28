package kakao.itstudy.mysqlserver.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface MemberService {
	//email 중복 검사를 위한 메소드
	public void emailCheck(HttpServletRequest request);

	//별명 중복 검사를 위한 메소드
	public void nicknameCheck(HttpServletRequest request);

	//회원 가입을 위한 메소드
	public void join(MultipartHttpServletRequest request);

}
