package kakao.itstudy.mysqlserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kakao.itstudy.mysqlserver.dao.ItemDAO;
import kakao.itstudy.mysqlserver.dao.MemberDAO;
import kakao.itstudy.mysqlserver.domain.Member;
import kakao.itstudy.mysqlserver.util.CryptoUtil;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDao;

	@Override
	@Transactional
	public void emailCheck(HttpServletRequest request) {
		//이메일 중복 체크 결과
		Map<String, Object> map = 
				new HashMap<String, Object>();
		map.put("result", false);

		String email = request.getParameter("email");

		if(email != null) {
			CryptoUtil util = new CryptoUtil();
			//입력한 email이 list에 없다면
			try {
				List<String> list = 
						memberDao.emailCheck(
								util.encrypt(email));
				if(list != null && list.size() == 0) {
					map.put("result", true);
				}
			}catch(Exception e) {}
		}
		request.setAttribute("result", map);

	}

	@Override
	@Transactional
	public void nicknameCheck(HttpServletRequest request) {
		//닉네임 중복 체크 결과
		Map<String, Object> map = 
				new HashMap<String, Object>();
		map.put("result", false);

		String nickname = request.getParameter("nickname");

		if(nickname != null) {
			List<String> list = 
					memberDao.nicknameCheck(nickname);
			if(list != null && list.size() == 0) {
				map.put("result", true);
			}
		}
		request.setAttribute("result", map);
	}

	@Override
	@Transactional
	public void join(MultipartHttpServletRequest request) {
		//결과를 저장할 Map을 생성
		Map<String, Object> map = 
				new HashMap<String, Object>();

		map.put("result", true);
		map.put("emailcheck", true);
		map.put("nicknamecheck", true);

		//필요한 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		String profile = "default.jpg";

		MultipartFile image = request.getFile("profile");
		try {
		//emailcheck
		if(email != null) {
			CryptoUtil util = new CryptoUtil();
			
			List<String> list = 
					memberDao.emailCheck(util.encrypt(email));
			if(list != null && list.size() != 0) {
				map.put("result", false);
				map.put("emailcheck",false);
			}else {
				if(nickname != null) {
					list = 
							memberDao.nicknameCheck(nickname);
					if(list != null && list.size() != 0) {
						map.put("result", false);
						map.put("nicknamecheck",false);
					}else {
						if(image != null && image.isEmpty() == false) {
							//파일을 저장할 디렉토리 경로를 생성
							String filePath = 
									request.getServletContext()
									.getRealPath("/profile");
							//파일 이름 생성
							profile = UUID.randomUUID() + 
									image.getOriginalFilename();
							//파일 저장 경로 생성
							filePath = filePath + "/" + profile;

							//파일 업로드
							try {
								File file = new File(filePath);
								FileOutputStream fos  = 
										new FileOutputStream(file);
								fos.write(image.getBytes());
								fos.close();
							}catch(Exception e) {}
						}

						Member member = new Member();
						//이메일은 복호화가 가능하도록 암호화를 해서 저장
						try {
							member.setEmail(util.encrypt(email));
						}catch(Exception e) {}

						//비밀번호는 복호화가 불가능하도록 저장
						member.setPw(BCrypt.gensalt());
						member.setNickname(nickname);
						member.setProfile(profile);

						Serializable result = memberDao.join(member);

						if(result == null) {
							map.put("result", false);
						}
					}
				}
			}
		}
		}catch(Exception e) {}
		request.setAttribute("result", map);
	}
}













