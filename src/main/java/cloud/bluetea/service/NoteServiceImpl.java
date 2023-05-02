package cloud.bluetea.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cloud.bluetea.domain.NoteVO;
import cloud.bluetea.mapper.NoteMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
	private NoteMapper noteMapper;
	@Override
	public NoteVO get(Long noteno) {
		// TODO Auto-generated method stub
		return noteMapper.selectOne(noteno);
	}
	@Override
	public List<NoteVO> getReceiveList(String id) {
		// TODO Auto-generated method stub
		return noteMapper.receiveList(id);
	}
	@Override
	public List<NoteVO> getReceiveUncheckedList(String id) {
		// TODO Auto-generated method stub
		return noteMapper.receiveUncheckedList(id);
	}
	@Override
	public List<NoteVO> getSendList(String id) {
		// TODO Auto-generated method stub
		return noteMapper.sendList(id);
	}
	@Override
	public int receive(Long noteno) {
		// TODO Auto-generated method stub
		return noteMapper.update(noteno);
	}
	@Override
	public int remove(Long noteno) {
		// TODO Auto-generated method stub
		return noteMapper.delete(noteno);
	}
	@Override
	public int send(NoteVO vo) {
		// TODO Auto-generated method stub
		return noteMapper.insert(vo);
	}
}
