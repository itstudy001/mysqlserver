package kakao.itstudy.mysqlserver.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ItemService {
	//목록 보기
	public void list(HttpServletRequest request);
	
	//상세 보기
	public void detail(HttpServletRequest request);
	
	//삽입 하기
	public void insert(MultipartHttpServletRequest request);
	
}
