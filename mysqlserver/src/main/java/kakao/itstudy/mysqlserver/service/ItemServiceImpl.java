package kakao.itstudy.mysqlserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

	@Override
	@Transactional
	public void detail(HttpServletRequest request) {
		int itemid = 0;
		//파라미터를 읽기
		String id = request.getParameter("itemid");
		//예외 발생 여부를 차단하기 위해서 itemid가 있는 
		//경우만 정수로 변경해서 저장
		if(id != null) {
			itemid = Integer.parseInt(id);
		}
		
		Item item = null;
		//DAO 메소드 호출해서 결과를 가져옵니다.
		item = itemDao.detail(itemid);
		
		//결과를 Map에 저장
		Map<String, Object>map = 
				new HashMap<String, Object>();
		//읽어온 데이터 저장
		map.put("item", item);
		//데이터 존재 여부
		map.put("result", false);
		if(map != null) {
			map.put("result", true);
		}
		//request에 저장
		request.setAttribute("result", map);
		
		
	}

	@Override
	@Transactional
	public void insert(MultipartHttpServletRequest request) {
		//ItemId를 생성
		int itemid = 1;
		//데이터 개수 가져오기
		Map<String, Object>map = 
				new HashMap<String, Object>();
		int count = itemDao.count(map);
		//데이터가 존재하는 경우는 가장 itemid + 1
		if(count != 0) {
			itemid = itemDao.maxid() + 1;
		}
		
		//결과를 저장할 맵
		Map<String, Object> result = 
			new HashMap<String, Object>();
		result.put("result", false);
		try {
			//파라미터 읽기
			String itemname = 
				request.getParameter("itemname");
			String description = 
				request.getParameter("description");
			int price = 
			Integer.parseInt(
				request.getParameter("price"));
			String pictureurl = "default.jpg";
			//전송된 이미지 파일을 가져오기
			MultipartFile image = 
					request.getFile("pictureurl");
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat();
			
			String updatedate = sdf.format(date);
			
			//파일을 저장할 디렉토리의 실제 경로를 추출
			//servlet 2.5 이하 일 때는 
			//getServletContext 생성
			String filePath = 
				request.getServletContext()
				.getRealPath("/img");
			
			//실행 시 파일이 업로드 되는 경로
			//프로젝트에는 업로드 안됩니다.
			//배포할 때 나중에 추가된 데이터는 전부 삭제해야 
			//합니다.
			System.err.println("업로드₩:" + filePath);
			
			//업로드된 파일 이름의 존재 여부 확인
			if(image != null && image.isEmpty() == false) {
				//파일 이름을 중복없이 생성
				pictureurl = UUID.randomUUID() + 
						image.getOriginalFilename();
				//실제 파일이 저장될 경로 생성
				File file = new File(
						filePath + "/" + pictureurl);
				
				//파일을 업로드
				FileOutputStream fos = 
						new FileOutputStream(file);
				fos.write(image.getBytes());
				fos.close();
			}
			
			//ItemDAO 메소드의 파라미터 생성
			Item item = new Item();
			item.setItemid(itemid);
			item.setItemname(itemname);
			item.setDescription(description);
			item.setPrice(price);
			item.setPictureurl(pictureurl);
			item.setUpdatedate(updatedate);
			
			//데이터 삽입 메소드 호출
			Serializable r = itemDao.insert(item);
			if(r != null) {
				map.put("result", true);
			}			
		}
		catch(Exception e) {
			result.put(
				"error", e.getLocalizedMessage());
		}
		
		request.setAttribute("result", map);
	}
}








