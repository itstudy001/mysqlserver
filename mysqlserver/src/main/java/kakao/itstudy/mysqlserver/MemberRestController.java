package kakao.itstudy.mysqlserver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kakao.itstudy.mysqlserver.service.MemberService;

@RestController
public class MemberRestController {
	@Autowired
	private MemberService memberService;

	//이메일 중복 검사
	@RequestMapping("member/emailcheck")
	public Map<String, Object> emailcheck(
			HttpServletRequest request){
		memberService.emailCheck(request);
		return (Map<String, Object>)
				request.getAttribute("result");
	}

	//별명 중복 검사
	@RequestMapping("member/nicknamecheck")
	public Map<String, Object> nicknamecheck(
			HttpServletRequest request){
		memberService.nicknameCheck(request);
		return (Map<String, Object>)
				request.getAttribute("result");
	}

	//회원 가입 검사
	@RequestMapping(value = "member/join",
			method=RequestMethod.POST)
	public Map<String, Object> join(
			MultipartHttpServletRequest request){
		memberService.join(request);
		return (Map<String, Object>)
				request.getAttribute("result");
	}

	//회원 가입 검사
	@RequestMapping(value = "member/login",
			method=RequestMethod.POST)
	public Map<String, Object> login(
			HttpServletRequest request){
		memberService.login(request);
		return (Map<String, Object>)
				request.getAttribute("result");
	}


}





