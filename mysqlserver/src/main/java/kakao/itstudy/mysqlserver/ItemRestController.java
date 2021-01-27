package kakao.itstudy.mysqlserver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kakao.itstudy.mysqlserver.service.ItemService;

@RestController
public class ItemRestController {
	@Autowired
	private ItemService itemService;
	
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
	
}
