package cloud.bluetea.mapper;

import java.util.List;

import cloud.bluetea.domain.AttachVO;

public interface AttachMapper {
	void insert(AttachVO vo);
	
	void delete(String uuid);
	
	List<AttachVO> findBy(Long bno);
	
	void deleteAll(Long bno);
	
	List<AttachVO> getOldFiles();
}
