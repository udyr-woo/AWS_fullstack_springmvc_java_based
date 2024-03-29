package cloud.bluetea.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cloud.bluetea.config.RootConfig;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
public class AttachMapperTests {
	@Autowired
	private AttachMapper attachMapper;
	
	@Test
	public void testGetOldFiles() {
		attachMapper.getOldFiles();
	}
}
