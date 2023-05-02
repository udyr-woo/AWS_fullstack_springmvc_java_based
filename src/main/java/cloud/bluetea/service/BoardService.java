package cloud.bluetea.service;

import java.util.List;

import cloud.bluetea.domain.AttachFileDTO;
import cloud.bluetea.domain.BoardVO;
import cloud.bluetea.domain.Criteria;

public interface BoardService {
	void register(BoardVO vo);
	BoardVO get(Long bno);
	boolean modify(BoardVO vo);
	boolean remove(Long bno);
	List<BoardVO> getList(Criteria cri);
	int getTotalCnt(Criteria cri);
	String deleteFile(AttachFileDTO dto);
}
