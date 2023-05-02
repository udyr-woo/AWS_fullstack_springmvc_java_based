package cloud.bluetea.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloud.bluetea.mapper.SampleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
@Transactional
public class SampleService {
	private SampleMapper mapper;
	
	public void addData(String data) {
		log.info("insert1()");
		mapper.insert1(data);
		log.info("insert2()");
		mapper.insert2(data);
		log.info("end");
	}
	
	public void addData2(String data) {
		log.info("insert1()");
		mapper.insert1(data);
		log.info("insert2()");
		mapper.insert2(data);
		log.info("insert2()");
		mapper.insert1(data);
		log.info("insert2()");
		mapper.insert2(data);
		log.info("end");
	}
}
