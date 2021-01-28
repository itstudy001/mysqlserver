package kakao.itstudy.mysqlserver.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kakao.itstudy.mysqlserver.domain.Item;

//DAO 클래스로 만들고 bean을 자동생성해주는 어노테이션
@Repository
public class ItemDAO {
	//동일한 자료형의 bean이 있으면 자동으로 주입받아주는 어노테이션
	@Autowired
	private SessionFactory sessionFactory;

	//전체 데이터 개수를 가져오는 메소드
	public int count(Map<String, Object> map) {
		int result = 0;
		//세션 가져오기
		Session session = 
				sessionFactory.getCurrentSession();
		//sql을 저장할 변수
		String sql = null;
		//하이버네이트 쿼리를 만들기 위한 변수
		Query query = null;

		//검색 항목 
		String searchtype = null;
		if(map.get("searchtype") != null) {
			searchtype = (String)map.get("searchtype");
		}
		//검색어
		String keyword = null;
		if(map.get("keyword") != null) {
			keyword = (String)map.get("keyword");
		}

		if(searchtype != null) {
			keyword = "%" + keyword + "%";
		}

		//searchtype 이 없으면 전체 데이터 개수
		if(searchtype == null) {
			sql = "select count(*) from item";
			query = session.createNativeQuery(sql);
		}else if(searchtype.equals("itemname")) {
			sql = "select count(*) from item "
					+ "where lower(itemname) like :keyword";
			query = session.createNativeQuery(sql);
			query.setParameter("keyword", keyword);
		}else if(searchtype.equals("description")) {
			sql = "select count(*) from item "
					+ "where lower(description) like :keyword";
			query = session.createNativeQuery(sql);
			query.setParameter("keyword", keyword);
		}else if(searchtype.equals("both")) {
			sql = "select count(*) from item "
					+ "where lower(description) like :description "
					+ "or lower(itemname) like :itemname";
			query = session.createNativeQuery(sql);
			query.setParameter("description", keyword);
			query.setParameter("itemname", keyword);
		}

		//쿼리 실행
		List<BigInteger> list = query.getResultList();

		//데이터가 나온 경우 정수로 변환
		if(list.size() > 0) {
			result = list.get(0).intValue();
		}

		return result;
	}

	//테이블에서 데이터 목록을 가져오는 메소드
	public List<Item> list(Map<String, Object>map){
		List<Item> result = new ArrayList<Item>();

		//파라미터 읽어오기
		int start = (Integer)map.get("start");
		int size = (Integer)map.get("size");

		//검색 항목 
		String searchtype = null;
		if(map.get("searchtype") != null) {
			searchtype = (String)map.get("searchtype");
		}
		//검색어
		String keyword = null;
		if(map.get("keyword") != null) {
			keyword = (String)map.get("keyword");
		}
		
		if(searchtype != null) {
			keyword = "%" + keyword + "%";
		}


		//세션 가져오기
		Session session = 
				sessionFactory.getCurrentSession();
		//sql을 저장할 변수
		String sql = null;
		//하이버네이트 쿼리를 만들기 위한 변수
		Query query = null;

		if(searchtype == null) {
			//수행할 SQL을 생성 
			sql = "select * from item "
					+ "order by itemid desc "
					+ "limit :start, :size";
			//하이버네이트가 실행하도록 쿼리를 생성 
			//하나의 행을 Item 클래스로 변환하도록 설정 
			query = session.createNativeQuery(
					sql, Item.class);
			query.setParameter("start", start);
			query.setParameter("size", size);
		}else if(searchtype.equals("itemname")) {
			//수행할 SQL을 생성 
			sql = "select * from item "
					+ "where lower(itemname) like :itemname "
					+ "order by itemid desc "
					+ "limit :start, :size";
			//하이버네이트가 실행하도록 쿼리를 생성 
			//하나의 행을 Item 클래스로 변환하도록 설정 
			query = session.createNativeQuery(
					sql, Item.class);
			query.setParameter("start", start);
			query.setParameter("size", size);
			query.setParameter("itemname", keyword);
		}else if(searchtype.equals("description")) {
			//수행할 SQL을 생성 
			sql = "select * from item "
					+ "where lower(description) like :description "
					+ "order by itemid desc "
					+ "limit :start, :size";
			//하이버네이트가 실행하도록 쿼리를 생성 
			//하나의 행을 Item 클래스로 변환하도록 설정 
			query = session.createNativeQuery(
					sql, Item.class);
			query.setParameter("start", start);
			query.setParameter("size", size);
			query.setParameter("description", keyword);
		}else if(searchtype.equals("both")) {
			//수행할 SQL을 생성 
			sql = "select * from item "
					+ "where lower(description) like :description "
					+ "or lower(itemname) like :itemname "
					+ "order by itemid desc "
					+ "limit :start, :size";
			//하이버네이트가 실행하도록 쿼리를 생성 
			//하나의 행을 Item 클래스로 변환하도록 설정 
			query = session.createNativeQuery(
					sql, Item.class);
			query.setParameter("start", start);
			query.setParameter("size", size);
			query.setParameter("description", keyword);
			query.setParameter("itemname", keyword);
		}

		//실행
		result = query.getResultList();

		return result;
	}

	//테이블에서 데이터 1개를 가져오는 메소드
	public Item detail(int itemid) {
		Item item = null;
		//기본키를 가지고 하나의 데이터 찾아오는 방법
		Session session = sessionFactory.getCurrentSession();
		item = session.get(Item.class, itemid);
		return item;
	}
	
	
	//가장 큰 itemid를 찾아오는 메소드
	public int maxid() {
		Session session = 
			sessionFactory.getCurrentSession();
		List<Integer> list = 
			session.createNativeQuery(
				"select max(itemid) from item")
			.getResultList();
		if(list == null || list.size() == 0) {
			return 0;
		}else {
			return list.get(0);
		}
	}
	
	//데이터 삽입을 위한 메소드
	//리턴 되는 값은 기본키 값입니다.
	public Serializable insert(Item item) {
		Session session = 
				sessionFactory.getCurrentSession();
		return session.save(item);
	}
}










