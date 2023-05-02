package cloud.bluetea.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cloud.bluetea.domain.AttachVO;
import cloud.bluetea.domain.BoardVO;
import cloud.bluetea.domain.Criteria;
import cloud.bluetea.domain.PageDto;
import cloud.bluetea.service.BoardService;
import lombok.Data;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("board/*")
@Data
public class BoardController {
	private final BoardService boardService;
	@GetMapping("list")
	public void list(Model model, Criteria cri) {
		log.info("list()");
		log.info(cri);
		model.addAttribute("list", boardService.getList(cri));
		model.addAttribute("page", new PageDto(boardService.getTotalCnt(cri), cri));
	}
	
	@GetMapping({"get", "modify"})
	public void get(Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("get() or modify()");
		log.info(bno);
		model.addAttribute("board",boardService.get(bno));
	}
	
	@GetMapping("{bno}")
	public String getByPath(@PathVariable Long bno, Model model) {
		log.info("get() or modify()");
		log.info(bno);
		model.addAttribute("board",boardService.get(bno));
		return "board/get";
	}
	
	@GetMapping("register")
	@PreAuthorize("isAuthenticated()")
	public void register() {}
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("register");
		log.info(vo);
		boardService.register(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		rttr.addAttribute("pageNum",1);
		rttr.addAttribute("amount",cri.getAmount());
		return "redirect:/board/list";
	}
	
	@PostMapping("modify")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.writer")
	public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("modify");
		log.info(vo);
		if(boardService.modify(vo)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and principal.username eq #writer")
	public String remove(String writer, Long bno, RedirectAttributes rttr, Criteria cri) {
		log.info("remove");
		log.info(bno);
		List<AttachVO> list = boardService.get(bno).getAttachs();
		if(boardService.remove(bno)) {
//			list.forEach(boardService::deleteFile);
			for(AttachVO vo : list) {
				boardService.deleteFile(vo);
			}
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
}
