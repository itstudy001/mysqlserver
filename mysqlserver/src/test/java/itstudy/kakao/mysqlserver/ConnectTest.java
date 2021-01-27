package itstudy.kakao.mysqlserver;

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

//설정 파일의 위치를 지정해서 설정 파일의 내용을 실행해서 테스트
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

public class ConnectTest {

	//데이터베이스 연결 객체
	@Autowired
	private DataSource dataSource;
	
	//하이버네이트 연결 객체
	@Autowired
	private SessionFactory factory;
	
	//ItemDAO 연결 객체
	@Autowired
	private ItemDAO itemDao;
	
	@Test
	@Transactional
	public void connectTest() throws Exception{
		//연결 객체의 생성 여부를 출력 
		//System.err.println(dataSource.getConnection().toString());
	
		//하이버네이트 사용 객체 출력
		//System.err.println(factory.toString());
		
		Map<String, Object> map = 
				new HashMap<String, Object>();
		map.put("start", 0);
		map.put("size", 5);
		map.put("searchtype","both");
		map.put("keyword", "비타민");
		
		System.err.println(
			"데이터 개수:" + itemDao.count(map));
		System.err.println(
			"데이터 목록:" + itemDao.list(map));
	}
}
