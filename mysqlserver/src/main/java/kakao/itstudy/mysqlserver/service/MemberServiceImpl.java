package kakao.itstudy.mysqlserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kakao.itstudy.mysqlserver.dao.ItemDAO;
import kakao.itstudy.mysqlserver.dao.MemberDAO;
import kakao.itstudy.mysqlserver.domain.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDao;

	@Override
	public void emailCheck(HttpServletRequest request) {
		//이메일 중복 체크 결과
		Map<String, Object> map = 
			new HashMap<String, Object>();
		map.put("result", false);
		
		String email = request.getParameter("email");
		
		if(email != null) {
			//입력한 email이 list에 없다면
			List<String> list = 
					memberDao.emailCheck(email);
			if(list != null && list.size() == 0) {
				map.put("result", true);
			}
		}
		request.setAttribute("result", map);
		
	}

	@Override
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
	public void join(MultipartHttpServletRequest request) {
		//결과를 저장할 Map을 생성
		Map<String, Object> map = 
			new HashMap<String, Object>();
		
		map.put("result", true);
		map.put("emailcheck", true);
		map.put("nicknamecheck", false);
		
		//필요한 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		String profile = "default.jpg";
		
		MultipartFile image = request.getFile("profile");
		
		//emailcheck
		if(email != null) {
			List<String> list = 
					memberDao.emailCheck(email);
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
						member.setEmail(email);
						member.setPw(pw);
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
		request.setAttribute("result", map);
	}
}













