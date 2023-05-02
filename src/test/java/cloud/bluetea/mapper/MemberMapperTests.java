package cloud.bluetea.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cloud.bluetea.domain.MemberVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTests {
	@Autowired
	private MemberMapper memberMapper;
	
//	@Test
//	public void testGetList() {
//		memberMapper.getList().forEach(log::info);
//	}
//	
//	@Test
//	public void testLogin() {
//		MemberVO vo = new MemberVO();
//		vo.setId("id1");
//		vo.setPw("12345");
//		log.info(memberMapper.login(vo));
//	}
}
