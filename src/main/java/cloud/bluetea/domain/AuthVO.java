package cloud.bluetea.domain;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("auth")
@Data
public class AuthVO {
	private String userid;
	private String auth;
}
