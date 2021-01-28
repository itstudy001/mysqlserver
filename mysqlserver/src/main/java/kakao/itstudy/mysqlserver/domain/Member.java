package kakao.itstudy.mysqlserver.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	private String email;
	private String pw;
	private String nickname;
	private String profile;
	private Date regdate;
	private Date logindate;
}
