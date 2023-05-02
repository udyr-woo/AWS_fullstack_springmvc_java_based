package cloud.bluetea.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.bluetea.domain.NoteVO;
import cloud.bluetea.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("note")
@AllArgsConstructor
public class NoteController {
	private NoteService noteService;
	
	@PostMapping("new")
	public int send(@RequestBody NoteVO vo) {
		log.info(vo);
		return noteService.send(vo);
	}
	
	@GetMapping("{noteno}")
	public NoteVO getNote(@PathVariable Long noteno) {
		log.info(noteno);
		return noteService.get(noteno);
	}
	
	@PutMapping("{noteno}")
	public int receive(@PathVariable Long noteno) {
		log.info(noteno);
		return noteService.receive(noteno);
	}
	@DeleteMapping("{noteno}")
	public int remove(@PathVariable Long noteno) {
		log.info(noteno);
		return noteService.remove(noteno);
	}
	
	@GetMapping("s/{id}")
	public List<NoteVO> getSendList(@PathVariable String id) {
		log.info(id);
		return noteService.getSendList(id);
	}
	
	@GetMapping("r/{id}")
	public List<NoteVO> getReceiveList(@PathVariable String id) {
		log.info(id);
		return noteService.getReceiveList(id);
	}
	
	@GetMapping("r/c/{id}")
	public List<NoteVO> getReceivecheckedList(@PathVariable String id) {
		log.info(id);
		return noteService.getReceiveUncheckedList(id);
	}
}
