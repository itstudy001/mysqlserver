package kakao.itstudy.mysqlserver.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kakao.itstudy.mysqlserver.domain.Item;
import kakao.itstudy.mysqlserver.domain.Member;

@Repository
public class MemberDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	//email 중복 체크를 위한 메소드
	public List<String> emailCheck(String email) {
		Session session = 
			sessionFactory.getCurrentSession();
		List<String> list = 
			session.createNativeQuery(
			"select email from member where email=:email")
			.setParameter("email", email)
			.getResultList();
		return list;
	}
	
	//nickname 중복 체크를 위한 메소드 
	public List<String> nicknameCheck(String nickname) {
		Session session = 
				sessionFactory.getCurrentSession();
		List<String> list = 
			session.createNativeQuery(
			"select nickname from member where nickname=:nickname")
			.setParameter("nickname", nickname)
			.getResultList();
		return list;
	}
	
	//회원 가입을 위한 메소드
	public Serializable join(Member member) {
		Session session = 
			sessionFactory.getCurrentSession();
		return session.save(member);
	}
	
	//로그인을 위한 메소드
	public Member login(String email) {
		Session session = sessionFactory.getCurrentSession();
		Member member = 
				session.get(Member.class, email);
		return member;
	}
}




