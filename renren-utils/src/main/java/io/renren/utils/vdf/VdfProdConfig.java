package io.renren.utils.vdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VdfProdConfig {
	private Logger logger = LoggerFactory.getLogger(VdfProdConfig.class);
	private Workbook wb;
	private Sheet sheet;
	private Row row;

	public VdfProdConfig(String filepath) {
		if (filepath == null) {
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 * @author zengwendong
	 */
	public String[] readExcelTitle() throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = row.getCell(i).getCellFormula();
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @author zengwendong
	 */
	public Map<Integer, Map<Integer, Object>> readExcelContent() throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();

		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			Map<Integer, Object> cellValue = new HashMap<Integer, Object>();
			while (j < colNum) {
				Object obj = getCellFormatValue(row.getCell(j));
				cellValue.put(j, obj);
				j++;
			}
			content.put(i, cellValue);
		}
		return content;
	}

	/**
	 * 
	 * 根据Cell类型设置数据
	 * 
	 * @param cell
	 * @return
	 * @author zengwendong
	 */
	@SuppressWarnings("deprecation")
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					Date date = cell.getDateCellValue();
					cellvalue = date;
				} else {// 如果是纯数字

					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	static int i = 100;

	static Map<Integer, Map<Integer, Object>> passwordmap = null;

	public static void printInsertSQL(Map<Integer, Object> map) {
		String strTemp = "INSERT INTO mm_product VALUES " + "(%s, '%s', '%s', 2, 2, 'IN',  %s,  %s, 'INR', '%s', '%s', 0.9000, '2018-11-27 16:12:17', '2018-11-27 16:12:17', '1', '1', 1, '%s', '%s',   '%s', "// 
				+ "'{\"code\":\"%s\", \"mode\":\"WAP_D2C\", \"ServiceID\":\"%s\", \"ServiceClass\":\"%s\", \"OrgId\":\"PAN05906\", \"username\":\"%s\", \"password\":\"%s\"}', %s);";// 

		if ("GAMEBOX_MONTHLYY".equals(map.get(1).toString())) {
			return;
		}
		if ("ASTRO_MONTHLY".equals(map.get(1).toString())) {
			return;
		}
		if ("FITNESS_MONTHLY".equals(map.get(1).toString())) {
			return;
		}
		if ("VIDEOBOX_MONTHLY".equals(map.get(1).toString())) {
			return;
		}
		if ("POCKETQUIZ_MONTHLY".equals(map.get(1).toString())) {
			return;
		}

		String[] price = map.get(6).toString().split("DAYS");
		String prodType = map.get(7).toString().equals("ACTIVATION") ? "0" : "1";
		Map<Integer, Object> password = getPassword(map.get(1) + "");
		String str = String.format(strTemp, new Object[] { i++, map.get(1).toString(), map.get(3).toString(), price[1], price[0], // 
				password.get(9), password.get(10), // 地址信息
				map.get(8).toString(), //
				map.get(9).toString(), map.get(6).toString(), //
				map.get(3).toString(), map.get(8).toString(), map.get(9).toString(), password.get(3), password.get(4), prodType });

		System.out.println(str);

	}

	public static Map<Integer, Object> getPassword(String productName) {
		for (int i = 1; i <= passwordmap.size(); i++) {
			if (productName.equals(passwordmap.get(i).get(2) + "")) {
				return passwordmap.get(i);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			String filepath = "F:\\code\\svn\\Global\\RockyMobi\\2_doc\\1_准备\\印度-Vodafone\\vodafone\\MSG_SDP_ProductName_CREATION_TEMPALTE.xlsx";
			String passwordmapexcelReaderfilepath = "F:\\code\\svn\\Global\\RockyMobi\\2_doc\\1_准备\\印度-Vodafone\\vodafone\\Vodafone Product Details-Vikram.xlsx";
			VdfProdConfig excelReader = new VdfProdConfig(filepath);
			// 对读取Excel表格内容测试
			Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent();

			VdfProdConfig passwordmapexcelReader = new VdfProdConfig(passwordmapexcelReaderfilepath);
			passwordmap = passwordmapexcelReader.readExcelContent();

			System.out.println("获得Excel表格的内容:");
			//			for (int i = 1; i <= map.size(); i++) {
			//				System.out.println(i + "→" + map.get(i));
			//			}
			for (int i = 23; i <= map.size(); i++) {
				if ("GPRS".equals(map.get(i).get(14) + "")) {
					printInsertSQL(map.get(i));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
