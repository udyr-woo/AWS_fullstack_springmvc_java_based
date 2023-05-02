package cloud.bluetea.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloud.bluetea.domain.ReplyVO;
import cloud.bluetea.mapper.BoardMapper;
import cloud.bluetea.mapper.ReplyMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	private ReplyMapper replyMapper;
	private BoardMapper boardMapper;
	
	@Override
	@Transactional
	public int register(ReplyVO vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		return replyMapper.insert(vo);
	}
	
	@Override
	public ReplyVO get(Long rno) {
		return replyMapper.read(rno);
	}
	
	@Override
	@Transactional
	public int remove(Long rno) {
		boardMapper.updateReplyCnt(get(rno).getBno(), -1);
		return replyMapper.delete(rno);
	}
	
	@Override
	public int modify(ReplyVO vo) {
		return replyMapper.update(vo);
	}
	
	@Override
	public List<ReplyVO> getList(Long bno, Long rno) {
		return replyMapper.getList(bno, rno);
	}
}
