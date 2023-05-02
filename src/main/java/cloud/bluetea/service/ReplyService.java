package cloud.bluetea.service;

import java.util.List;

import cloud.bluetea.domain.ReplyVO;

public interface ReplyService {
	int register(ReplyVO vo);
	
	ReplyVO get(Long rno);
	
	int remove(Long rno);
	
	int modify(ReplyVO vo);
	
	List<ReplyVO> getList(Long bno, Long rno);
}
