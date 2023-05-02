package cloud.bluetea.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cloud.bluetea.domain.BoardVO;
import cloud.bluetea.domain.Criteria;

public interface BoardMapper {
	List<BoardVO> getList();
	
	List<BoardVO> getListWithPaging(Criteria cri);

	int getTotalCnt(Criteria cri);
	
	void insert(BoardVO vo);
	
	void insertSelectKey(BoardVO vo);
	
	BoardVO read(Long bno);
	
	int delete(Long bno);
	
	int update(BoardVO vo);
	
	void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
