package cloud.bluetea.service;

import java.util.List;

import cloud.bluetea.domain.NoteVO;
import cloud.bluetea.mapper.NoteMapper;

public interface NoteService {
	int send(NoteVO vo);
	
	NoteVO get(Long noteno);
	
	int receive(Long noteno);
	
	int remove(Long noteno);
	
	List<NoteVO> getSendList(String id);
	
	List<NoteVO> getReceiveList(String id);
	
	List<NoteVO> getReceiveUncheckedList(String id);
	
}
