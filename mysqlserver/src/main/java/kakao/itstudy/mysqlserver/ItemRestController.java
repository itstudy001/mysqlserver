package kakao.itstudy.mysqlserver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kakao.itstudy.mysqlserver.service.ItemService;

@RestController
public class ItemRestController {
	@Autowired
	private ItemService itemService;

	//목록 보기
	@RequestMapping("item/list")
	public Map<String, Object> list(
			HttpServletRequest request){
		//서비스 호출 
		itemService.list(request);
		Map<String, Object> map = 
				(Map<String, Object>)
				request.getAttribute("result");
		return map;
	}

	//상세 보기
	@RequestMapping("item/detail")
	public Map<String, Object> detail(
			HttpServletRequest request){
		//서비스 호출 
		itemService.detail(request);
		Map<String, Object> map = 
				(Map<String, Object>)
				request.getAttribute("result");
		return map;
	}

	//상세 보기
	@RequestMapping(value="item/insert", method=RequestMethod.POST)
	public Map<String, Object> insert(
			MultipartHttpServletRequest request){
		//서비스 호출 
		itemService.insert(request);
		Map<String, Object> map = 
				(Map<String, Object>)
				request.getAttribute("result");
		return map;
	}

}
