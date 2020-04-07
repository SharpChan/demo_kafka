package com.example.demo;

import com.example.demo.entity.CookieEntity;
import com.example.demo.entity.ProjectEntity;
import com.example.demo.entity.RowEntity;
import com.example.demo.intf.CreateExcelIntf;
import com.example.demo.intf.HttpTookitIntf;
import com.example.demo.intf.TableParseIntf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private TableParseIntf tableParseServer;

	@Autowired
	private HttpTookitIntf httpTookitService;

	@Autowired
	private CreateExcelIntf createExcelService;

	@Test
	void testPost() {
		CookieEntity cookieEntity = new CookieEntity();
		cookieEntity.setZa("za=chengfeng;");
		cookieEntity.setZentaosid("zentaosid=rnrj3ijo8laivd3tikntc01gv7");
		String tableStr = httpTookitService.sendPost("http://10.10.10.151/zentao/company-effort-custom-date_desc.html",cookieEntity.toString(),"","","");
		List<RowEntity> rowEntityList = tableParseServer.parseTable(tableStr);
		System.out.println("输出rowEntityList：" + rowEntityList);
	}
	@Test
	void testLogInGetCookie() {

		List<String> list =new ArrayList<String>();
		String s = httpTookitService.logInGetCookie("chengfeng","12345678",list);
		String  aaa = list.get(list.size()-1);
		String[] arr = aaa.split(";");

		CookieEntity cookieEntity = new CookieEntity();
		cookieEntity.setZa("za=chengfeng;");
		cookieEntity.setZentaosid(arr[0]);
		String tableStr = httpTookitService.sendPost("http://10.10.10.151/zentao/company-effort-custom-date_desc.html",cookieEntity.toString(),"","","");
		List<RowEntity> rowEntityList = tableParseServer.parseTable(tableStr);
		System.out.println("输出rowEntityList：" + rowEntityList);

		List<ProjectEntity> ProjectEntityList = createExcelService.formatConverter(rowEntityList);
		System.out.println(ProjectEntityList);
	}

	@Test
	public void oldRunable() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("The old runable now is using!");
			}
		}).start();
	}
}
