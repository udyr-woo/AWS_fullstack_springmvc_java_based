package cloud.bluetea.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@Alias("attach")
public class AttachVO extends AttachFileDTO {
	private Long bno;
}
