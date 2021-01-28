package itstudy.kakao.mysqlserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kakao.itstudy.mysqlserver.dao.ItemDAO;
import kakao.itstudy.mysqlserver.domain.Item;

//설정 파일의 위치를 지정해서 설정 파일의 내용을 실행해서 테스트
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

public class ItemTest {
	//ItemDAO 연결 객체
	@Autowired
	private ItemDAO itemDao;
	
	@Test
	@Transactional
	public void itemTest() {
		/*
		//존재하면 데이터가 리턴됩니다.
		System.err.println(itemDao.detail(1));
		//존재하지 않는 번호를 대입하면 null 이 리턴
		System.err.println(itemDao.detail(10));
		*/
		
		//가장 큰 itemid를 가져와서 출력
		int maxid = itemDao.maxid();
		System.err.println("가장 큰 ID:" + maxid);
		
		//삽입할 데이터 생성
		Item item = new Item();
		item.setItemid(maxid + 1);
		item.setItemname("배");
		item.setDescription("수분이 많은 과일");
		item.setPrice(5000);
		item.setPictureurl("pear.jpg");
		Date date = new Date();
		SimpleDateFormat sdf = 
			new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		item.setUpdatedate(sdf.format(date));
		
		//데이터 삽입
		System.err.println(itemDao.insert(item));
		
	}
}










