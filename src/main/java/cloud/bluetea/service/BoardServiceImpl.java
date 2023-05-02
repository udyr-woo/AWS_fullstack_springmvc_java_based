package cloud.bluetea.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloud.bluetea.controller.UploadController;
import cloud.bluetea.domain.AttachFileDTO;
import cloud.bluetea.domain.AttachVO;
import cloud.bluetea.domain.BoardVO;
import cloud.bluetea.domain.Criteria;
import cloud.bluetea.mapper.AttachMapper;
import cloud.bluetea.mapper.BoardMapper;
import cloud.bluetea.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
@ToString
public class BoardServiceImpl implements BoardService {
	private ReplyMapper replyMapper;
	private final BoardMapper boardMapper; 
	private final AttachMapper attachMapper; 
	
	@Override
	@Transactional
	public void register(BoardVO vo) {
		boardMapper.insertSelectKey(vo);
		Long bno = vo.getBno();
		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list) {
			attach.setBno(bno);
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
	}
	
	@Override
	public BoardVO get(Long bno) {
		return boardMapper.read(bno);
	}
	
	@Override
	@Transactional
	public boolean modify(BoardVO vo) {
		attachMapper.deleteAll(vo.getBno());
		
		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list) {
			attach.setBno(vo.getBno());
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
		
		return boardMapper.update(vo) > 0;
	}
	
	@Override
	@Transactional
	public boolean remove(Long bno) {
		replyMapper.deleteByBno(bno);
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) > 0;
	}
	
	@Override
	public List<BoardVO> getList(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}
	
	@Override
	public int getTotalCnt(Criteria cri) {
		return boardMapper.getTotalCnt(cri);
	}
	
	@Override
	public String deleteFile(AttachFileDTO dto) {
		log.info(dto);
		
		String s = UploadController.getPATH() + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
		
		File file = new File(s);
		file.delete();
		if(dto.isImage()) {
			s = UploadController.getPATH() + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
			file = new File(s);
			file.delete();
		}
		return dto.getUuid();
	}
}
