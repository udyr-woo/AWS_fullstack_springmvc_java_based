package cloud.bluetea.mapper;

import cloud.bluetea.domain.MemberVO;

public interface MemberMapper {
	public MemberVO read(String userid);
}
