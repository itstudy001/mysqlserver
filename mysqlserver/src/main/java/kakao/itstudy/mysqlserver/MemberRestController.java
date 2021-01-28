package kakao.itstudy.mysqlserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import kakao.itstudy.mysqlserver.service.MemberService;

@RestController
public class MemberRestController {
	@Autowired
	private MemberService memberService;

}
