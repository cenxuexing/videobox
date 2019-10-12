package io.renren.rockmobi.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AutoReNewSubscribeTest {

//	@Autowired
//	private MmProductOrderService mmProductOrderService;
//	
//	private static final String OPERATOR_CODE = "smarttel";
//
//	private static final String COUNTRY = "尼泊尔";
	
	@Test
	public void test1() throws Exception {
		
		Date nowDate = new Date();
		System.out.println(nowDate);
//		List<MmProductOrderEntity> list = mmProductOrderService.listQueryProductOrder(COUNTRY, OPERATOR_CODE);
//		//根据手机号分组
//		Map<String, List<MmProductOrderEntity>> groupMaps = list.stream().collect(Collectors.groupingBy(MmProductOrderEntity::getUserPhone));
//		for (String phone : groupMaps.keySet()) {
//			List<MmProductOrderEntity> mpoes = groupMaps.get(phone);
//			System.out.println("userPhone: " + phone + "list: " + mpoes.size());
//			for (MmProductOrderEntity mpoe : mpoes) {
//				System.out.println("userPhone: " + phone + "last action: " + mpoe.getOrderType());
//			}
//		}
		
	}
	
	

	
	
}
