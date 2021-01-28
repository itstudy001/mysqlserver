package kakao.itstudy.mysqlserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kakao.itstudy.mysqlserver.dao.ItemDAO;
import kakao.itstudy.mysqlserver.dao.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDao;
}
