package cloud.bluetea.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cloud.bluetea.domain.CustomUser;
import cloud.bluetea.domain.MemberVO;
import cloud.bluetea.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn(username);
		MemberVO vo = memberMapper.read(username);
		
		return vo == null ? null : new CustomUser(vo) ;
	}
}
