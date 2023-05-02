package cloud.bluetea.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cloud.bluetea.domain.NoteVO;

public interface NoteMapper {
	@Insert("INSERT INTO TBL_NOTE (NOTENO, SENDER, RECEIVER, MESSAGE) values(seq_note.nextval, #{sender}, #{receiver}, #{message})")
	int insert(NoteVO vo);
	
	@Select("SELECT * FROM TBL_NOTE WHERE NOTENO = #{noteno}")
	NoteVO selectOne(Long noteno);
	
	@Update("UPDATE TBL_NOTE SET RDATE = SYSDATE WHERE NOTENO = #{noteno}")
	int update(Long note);
	
	@Delete("DELETE FROM TBL_NOTE WHERE NOTENO = #{noteno}")
	int delete(Long noteno);
	
	@Select("SELECT * FROM TBL_NOTE tn WHERE NOTENO > 0 AND SENDER = #{sender} ORDER BY 1 DESC")
	List<NoteVO> sendList(String sender);
	
	@Select("SELECT * FROM TBL_NOTE tn WHERE NOTENO > 0 AND RECEIVER  = #{receiver} ORDER BY 1 DESC")
	List<NoteVO> receiveList(String receiver);
	
	@Select("SELECT * FROM TBL_NOTE tn WHERE NOTENO > 0 AND RECEIVER  = #{receiver} AND RDATE  IS NULL ORDER BY 1 DESC")
	List<NoteVO> receiveUncheckedList(String receiver);
}
