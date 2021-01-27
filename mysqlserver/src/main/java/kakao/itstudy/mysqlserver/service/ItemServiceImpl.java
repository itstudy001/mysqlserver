package kakao.itstudy.mysqlserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kakao.itstudy.mysqlserver.dao.ItemDAO;
import kakao.itstudy.mysqlserver.domain.Item;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemDAO itemDao;

	@Override
	@Transactional
	public void list(HttpServletRequest request) {
		//클라이언트가 전송한 파라미터를 읽기 
		//페이지 번호, 데이터 개수, 검색항목, 검색어
		int pageno = 1;
		//페이지 번호가 있는 경우에만 페이지 번호를 설정하고
		//그 이외의 경우는 1
		String pn = request.getParameter("pageno");
		if(pn != null) {
			pageno = Integer.parseInt(pn);
		}
		//데이터 개수 설정
		int size = 5;
		String count = request.getParameter("count");
		if(count != null) {
			size = Integer.parseInt(count);
		}
		
		//실제 필요한 것은 시작하는 번호
		int start = (pageno-1) * size;
		
		//나머지 파라미터 읽기
		String searchtype = 
				request.getParameter("searchtype");
		String keyword = 
				request.getParameter("keyword");
		
		//DAO를 호출해야 하면 DAO 호출할 메소드 파라미터를 생성
		Map<String, Object>map = 
			new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		map.put("searchtype", searchtype);
		map.put("keyword", keyword);
		
		//DAO 메소드를 호출해서 결과를 받습니다.
		int datacount = itemDao.count(map);
		List<Item> list = itemDao.list(map);
		
		//Controller로 리턴한 데이터를 만들던가 
		//클라이언트에 출력할 데이터를 request에 저장
		Map<String, Object> result = 
				new HashMap<String, Object>();
		result.put("count", datacount);
		result.put("list", list);
		request.setAttribute("result", result);
		
		
		
		
	}
}
