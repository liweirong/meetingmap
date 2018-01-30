package export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javassist.Modifier;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import com.ibm.icu.text.SimpleDateFormat;

public class ExcelUtils {
	private int startNum;
	private int maxNum;
	private HttpServletResponse response;

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;

	private HSSFRow row;

	private String fileName;

	private String[] header;
	private String title;
	private int lineNum;  //从0开始
	private HSSFCellStyle contentCellStyle;
	private int [][] storeWidth = new int[100][1];
	
	/**
	 * 通过读取国际化properties得到的哈希映射表
	 */
	private Map<String, Map<String, String>> attribute;
	
	/**
	 * 传过来的bean的集合
	 */
	private List<?> exportDateList;

	public ExcelUtils(HttpServletResponse response) {
		this.response = response;
		try {
			workbook = new HSSFWorkbook();
			contentCellStyle = this.getWorkbook().createCellStyle();
			contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//填充的列号
	private int indexLine = 0;
	/**
	 * 报表导出ZIP格式
	 * @param fileName
	 * @param title
	 * @param header
	 * @param filePath 生成文件的路径
	 */
	public void exportZIP(String fileName, String title, String sources, String[] header, String filePath) {
		try {
			this.fileName = fileName;
			this.header = header;
			this.title = title;
			setConfigurationZIP(fileName);
			setTitle(title,sources);
			setHeader(header);
			setContent();
			handleLastFile(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置下载文件为zip格式
	 * @param fileName
	 * @throws UnsupportedEncodingException 
	 */
	void setConfigurationZIP(String fileName) throws UnsupportedEncodingException {
		//response.setContentType("ms-excel");
//		response.setContentType("application/ms-excel" );
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + ".zip");
		sheet = workbook.createSheet(fileName);
		
	}
	
	/**
	 * 生成 file文件
	 * @throws Exception
	 */
	private void handleLastFile(String path) throws Exception {
//		for (int j = 0; j < header.length; j++) {
//			storeWidth[j][0] = storeWidth[j][0] == 0?1:storeWidth[j][0];
//			if (storeWidth[j][0] < 4){ 
//				getSheet().setColumnWidth(j,(storeWidth[j][0])*800);			
//			}if(storeWidth[j][0] > 255){
//				getSheet().setColumnWidth(j,(storeWidth[j][0]));			
//			} 
//			else
//				getSheet().setColumnWidth(j,(storeWidth[j][0])*400);
//			getSheet().autoSizeColumn(j);  
//			}
		//在指定目录生成文件
		File file = new File( path + ".xls");
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
	}
	
	void setConfiguration(String fileName) throws UnsupportedEncodingException {
		//response.setContentType("ms-excel");
		response.setContentType("application/ms-excel" );

		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
		sheet = workbook.createSheet(fileName);
		
	}

	//导出规则管理用此方法
	void setConfigurationRuleEdit(String fileName) throws UnsupportedEncodingException {
		//response.setContentType("ms-excel");
		response.setContentType("application/ms-excel" );

		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String( "规则管理".getBytes("gb2312"), "ISO8859-1" ) + ".xls");
		sheet = workbook.createSheet(fileName);
		
	}
	
	@SuppressWarnings("deprecation")
	void setTitle(String title,String sources) throws UnsupportedEncodingException {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFCellStyle styleCondition = workbook.createCellStyle();
		HSSFCellStyle styleTime = workbook.createCellStyle();
		styleTime.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)16);
		font.setColor(HSSFColor.BLUE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setBoldweight((short)10);
		style.setFont(font);
		int strLen =5;
		sheet.addMergedRegion(new Region(0,(short)0,1,(short)20)); //标题
		sheet.addMergedRegion(new Region(2,(short)2,2,(short)20));//内容
		sheet.addMergedRegion(new Region(3,(short)3,3,(short)6)); //时间
		//第几行，第几个单元格，第几行，第几个单元格（从0开始）
		sheet.createFreezePane( 2, 5);  //列数，行数（从1开始）
		HSSFRow row = sheet.createRow(0); //从0开始
		HSSFCell cell = row.createCell(0); //从0开始
		cell.setCellValue(this.title);
		cell.setCellStyle(style);
		 row = sheet.createRow(2); //从0开始
		 cell = row.createCell(2); //从0开始

		if (sources.contains("于")){
			strLen = sources.split("于").length*2;
		}
		 sheet.getRow(2).setHeightInPoints((short) ((short) strLen*10));
		 cell.setCellValue(sources);
		 styleCondition.setAlignment( HSSFCellStyle.ALIGN_LEFT);
		 styleCondition.setWrapText(true);
		 cell.setCellStyle(styleCondition);
		 
		 row = sheet.createRow(3); //从0开始
		 cell = row.createCell(3); //从0开始
		 cell.setCellStyle(styleTime);
		 cell.setCellValue("制表时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		 style= workbook.createCellStyle();
	
		 style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		 
		 cell.setCellStyle(style);
		 
		lineNum = 3;

	}

	void setTitle(String title) throws UnsupportedEncodingException {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)16);
		font.setColor(HSSFColor.BLUE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setBoldweight((short)10);
		style.setFont(font);
		
		sheet.addMergedRegion(new Region(0,(short)0,1,(short)(header.length-1))); //标题
		sheet.addMergedRegion(new Region(2,(short)0,2,(short)(header.length-1)));//时间
		//第几行，第几个单元格，第几行，第几个单元格（从0开始）
		sheet.createFreezePane( 2, 4);  //列数，行数（从1开始）
		HSSFRow row = sheet.createRow(0); //从0开始
		HSSFCell cell = row.createCell(0); //从0开始
		cell.setCellValue(this.title);
		cell.setCellStyle(style);
		
		 row = sheet.createRow(2); //从0开始
		 cell = row.createCell(0); //从0开始
		 cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		 style= workbook.createCellStyle();
		 style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		 
		 cell.setCellStyle(style);
		 
		lineNum = 2;
	}

	void setHeader(String[] headers) throws Exception {

		row = sheet.createRow(++lineNum);

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		Font font = workbook.createFont();
		font.setColor(HSSFColor.YELLOW.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style.setFont(font);
		
		for (int i = 0; i < headers.length; i++) {

			Cell cell = row.createCell(i);
			cell.setCellValue(headers[i]);	
			cell.setCellStyle(style);
			setMaxWidth(i, headers[i]);
		}

	}
	
	/**
	 * 该方法是为了兼容老版本的回调，现在已经修改为非抽象空方法,可以覆盖
	 * @throws Exception
	 */
	public void setContent() throws Exception{
		return;
	}
	
	/**
	 * 根据传来的bean填充excel,目前支持Ingter,String,Long,Date的填充
	 * @param exportDateList bean的list集合
	 * @throws Exception
	 */
	private void setContent(List<?> exportDateList) throws Exception{
		/**
		 * indexLine : 确定填充单元格的位置,每获取一个有效的数据就+1 cosmopolisContent(field, val,
		 * row); : 往单元格中填充数据的方法
		 * 
		 */
		try {
			// 拿到样式
			CellStyle cellStyle = this.getWorkbook().createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for (int i = 0; i < exportDateList.size(); i++) {
				Row row = getNextRow();
				Object obj = exportDateList.get(i);// 获取数据
				Class<? extends Object> clazz = obj.getClass();
				// 拿到所有的属性
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (!("RULEEDIT_MAXNUM".equals(field.getName()) 
							//访问者别名导出去掉serialVersionUID、aliasType  //审计对象别名导出去掉serialVersionUID、identcol
						|| "aliasType".equals(field.getName()) || "serialVersionUID".equals(field.getName()) || "identcol".equals(field.getName()) 
							//审计对象部分的导出需要屏蔽的字段
						|| "AUDITBUSOBJ_MAXNUM".equals(field.getName())
						|| "orgName".equals(field.getName())
						|| "busType".equals(field.getName())
						|| "busVers".equals(field.getName())
						|| "runEnvir".equals(field.getName())
						|| "devkey".equals(field.getName())
						|| "interdictIpString".equals(field.getName())
						|| "endTime".equals(field.getName())
						|| "ruleWhiteID".equals(field.getName())
						|| "controlOpt".equals(field.getName())
						|| "startTime".equals(field.getName())
						|| "category".equals(field.getName())
						|| "hisManufacturerName".equals(field.getName())
						//规则管理导出需要屏蔽的字段
						|| "trustIp".equals(field.getName()) 
						|| "trustIpToString".equals(field.getName()) 
						|| "tableNumLim".equals(field.getName()) 
						|| "messageId".equals(field.getName()) 
						|| "effectTime".equals(field.getName()) 
						|| "query".equals(field.getName()) 
						|| "funcWarn".equals(field.getName()) 
						|| "relationRule".equals(field.getName()) 
						|| "timeRang".equals(field.getName()) 
						|| "triggerNum".equals(field.getName()) 
						|| "mounthStaticNum".equals(field.getName()) 
					 )) {
						// 如果type是String
						if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
							indexLine++;
							continue;
						}
						if (field.getGenericType().toString().equals("class java.lang.String")) {
							// 拿到该属性的gettet方法
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							String val = (String) m.invoke(obj);
							// 开始填充
							cosmopolisContent(field, val, row);
							indexLine++;
						} else if (field.getGenericType().toString().equals("class java.lang.Integer")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							Integer val = (Integer) m.invoke(obj);
							// 对审计对象里的 状态、数据库版本、数据库类型进行翻译
							if ("busVersID".equals(field.getName()) || "busTypeID".equals(field.getName())) {
								String chineseString = null;
								chineseString = translation(val, field.getName());
								cosmopolisContent(field, chineseString, row);
							} else {
								cosmopolisContent(field, String.valueOf(val), row);
							}
							indexLine++;
						} else if (field.getGenericType().toString().equals("class java.lang.Double")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							Double val = (Double) m.invoke(obj);
							// 开始填充
							cosmopolisContent(field, String.valueOf(val), row);
							indexLine++;
						} else if (field.getGenericType().toString().equals("class java.lang.Long")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							Long val = (Long) m.invoke(obj);
							// 开始填充
							cosmopolisContent(field, String.valueOf(val), row);
							indexLine++;
						} else if (field.getGenericType().toString().equals("class java.util.Date")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							Date date = (Date) m.invoke(obj);
							if (date == null) {
								addCell(row, "未设置时间", indexLine);
								indexLine++;
								continue;
							}
							addCell(row, date.toLocaleString(), indexLine);
							indexLine++;
						} else if (field.getGenericType().toString().equals("long")) {
							if (field.getName().equals("serialVersionUID")) {
								addCell(row, "-", indexLine);
							}
							indexLine++;
							continue;
						} else if (field.getGenericType().toString().equals("class java.sql.Timestamp")) {
							Method m = (Method) obj.getClass().getMethod("get" + getMethodName(field.getName()));
							// 调用getter方法获取属性值
							Date date = (Date) m.invoke(obj);
							if (date == null) {
								addCell(row, "未设置时间", indexLine);
								indexLine++;
								continue;
							}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String format = sdf.format(date);
							addCell(row, format, indexLine);

							// addCell(row, date.toLocaleString(), indexLine);
							indexLine++;
						}
						
					}
				}
				indexLine = 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 此方法用于翻译导出审计对象报表中的一些数字
	 * 
	 * @param val
	 *            需要翻译的数值
	 * @param name
	 *            需要翻译的字段
	 * @return
	 */
	private String translation(Integer val, String name) {
		String busVersID = null;
		String busTypeID = null;
		if ("busVersID".equals(name)) { // bus_version_num表中的数据按id排序
			String s = "SQLSERVER2016,SQLSERVER2014,SQLSERVER2012,SQLSERVER2008,MySQL 5.7,MySQL 5.6,MySQL 5.5,MySQL 5.1,MySQL 5.0,MySQL 4.1,MySQL 4.0,Oracle 12C,Oracle 11g,Oracle 10g,Oracle 9i,Sybase,DB2 9.5,Informix 11.7,PostgreSQL 8.3,Cache2015,Cache2010,Cache5.2.4,Samba,Portal2015,Portal2010,Portal5.2.4,Http,DM 6.0,DM 7.0,Informix 11.7,GBase 8a,FTP,POP3, SMTP,神通数据库,NFS,SQLSERVER2005,SQLSERVER2000,Oracle 8i,1.0";
			String[] BusVersionNum = s.split(",");
			for (int i = 1; i < BusVersionNum.length; i++) {
				if (i == val) {
					busVersID = BusVersionNum[i - 1];
				}
			}
			return busVersID;
		} else if ("busTypeID".equals(name)) {// 审计对象中的数据库类型 bus_spec_category表中的数据
			switch (val) {
			case 1:
				busTypeID = "SQLSEVER";
				break;
			case 2:
				busTypeID = "MySQL";
				break;
			case 3:
				busTypeID = "Oracle";
				break;
			case 4:
				busTypeID = "Sybase";
				break;
			case 5:
				busTypeID = "DB2";
				break;
			case 6:
				busTypeID = "Informix";
				break;
			case 7:
				busTypeID = "PostgreSQL";
				break;
			case 8:
				busTypeID = "Cache";
				break;
			case 9:
				busTypeID = "Samba";
				break;
			case 10:
				busTypeID = "Portal";
				break;
			case 11:
				busTypeID = "Http";
				break;
			case 12:
				busTypeID = "DM";
				break;
			case 13:
				busTypeID = "人大金仓";
				break;
			case 14:
				busTypeID = "神通数据库";
				break;
			case 15:
				busTypeID = "FTP";
				break;
			case 16:
				busTypeID = "POP3";
				break;
			case 17:
				busTypeID = "SMTP";
				break;
			case 18:
				busTypeID = "中间件";
				break;
			case 19:
				busTypeID = "南大通用";
				break;
			case 20:
				busTypeID = "NFS";
				break;
			default:
				busTypeID = val + "";
				break;
			}
			return busTypeID;
		} else {
			return val + "";
		}

	}

	/**
	 * 把一个字符串的第一个字母大写、效率是最高的
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	public static String getMethodName(String fildeName) throws Exception{
		 byte[] items = fildeName.getBytes();
		 items[0] = (byte) ((char) items[0] - 'a' + 'A');
		 return new String(items);
	}
	
	/**
	 * 带分表的构造方法
	 * @param response
	 * @param startNum
	 * @param maxNum
	 */
	public ExcelUtils(HttpServletResponse response, int startNum, int maxNum) {
		this.response = response;
		try {
			workbook = new HSSFWorkbook();
			contentCellStyle = this.getWorkbook().createCellStyle();
			contentCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			this.startNum = startNum;
			this.maxNum = maxNum;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void export(String fileName, String title,String sources, String[] header) {
		try {

			this.fileName = fileName;
			this.header = header;
			this.title = title;
			setConfiguration(fileName);
			setTitle( title,sources);
			setHeader(header);
			setContent();
			handleLast();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				workbook.write(response.getOutputStream());
				releaseResource();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void export(String fileName, String title,String[] header) {
		try {

			this.fileName = fileName;
			this.header = header;
			this.title = title;
			setConfiguration(fileName);
			setTitle( title);
			setHeader(header);
			setContent();
			handleLast();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				workbook.write(response.getOutputStream());
				releaseResource();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 自动封装bean的sheet,建议直接使用该方法导出excel表
	 * @param title 标题
	 * @param exportDateList 列的数据
	 * @param path properties路径
	 * @param isClose 是否完成写入
	 */
	public void export(String title, List<?> exportDateList, String path, boolean isClose) {
		if(exportDateList == null || exportDateList.size() ==0){
			try {
				if(isClose){
					workbook.write(response.getOutputStream());
					releaseResource();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			//解析得到map
			//拿到对应的中文别名properties的目录
			//ParseEnumProperties properties = new ParseEnumProperties(path, exportDateList.get(0).getClass().getSimpleName());
			//得到要翻译的中文别名的map
			attribute = properties.getParseMap();
			this.exportDateList = exportDateList;
			cosmopolisSheet();
			this.title = title;
			setConfiguration(fileName);
			cosmopolisHeader();//定义表中列,根据com/audit/chinese.properties 文件得来
			setTitle(title);
			setHeader(header);
			setContent(exportDateList);//往单元格里填充数据库中数据
			handleLast();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(isClose){
					workbook.write(response.getOutputStream());
					releaseResource();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//导出规则管理用此方法
	public void exportRuleEdit(String title, List<?> exportDateList, String path, boolean isClose) {
		if(exportDateList == null || exportDateList.size() ==0){
			try {
				if(isClose){
					workbook.write(response.getOutputStream());
					releaseResource();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			//解析得到map
			//拿到对应的中文别名properties的目录
			ParseEnumProperties properties = new ParseEnumProperties(path, exportDateList.get(0).getClass().getSimpleName());
			//得到要翻译的中文别名的map
			attribute = properties.getParseMap();
			this.exportDateList = exportDateList;
			cosmopolisSheet();
			this.title = title;
			setConfigurationRuleEdit(fileName);
			cosmopolisHeader();
			setTitle(title);
			setHeader(header);
			setContent(exportDateList);
			handleLast();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(isClose){
					workbook.write(response.getOutputStream());
					releaseResource();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleLast() throws Exception {
		for (int j = 0; j < header.length; j++) {
			//导出Excel表格时表头为中文时getSheet().autoSizeColumn(j);此方法自定义列宽不生效
			//表格没内容时getSheet().autoSizeColumn(j);表头中文显示格式有问题
			getSheet().setColumnWidth(j,header[j].getBytes().length*2*256);
		}
		
	}

	public void releaseResource() throws Exception {
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	public void  setMaxWidth(int index,String str){  //设置每列的最大宽度
		
		if (!StringUtils.isBlank(str) && storeWidth[index][0] < str.length()) {
			storeWidth[index][0] = str.length();
		}
	}

	

	public HSSFSheet getSheet() {
		return sheet;
	}
	
	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	/**
	 * 增加单元格，填充内容
	 * @param row
	 * @param desc
	 * @param index
	 * @return
	 */
	public  Cell addCell(Row row,String desc,int index) {
		// TODO Auto-generated method stub
		Cell cell = row.createCell(index);
		cell.setCellValue(desc);
		cell.setCellStyle(contentCellStyle);
		setMaxWidth(index, desc);
		return cell;
	}
	
	protected Row getNextRow(){
		
		return workbook.getSheet(fileName).createRow(++lineNum);
	}
	
	/**
	 * 国际化Content方法，用于将需要的字段国际化
	 * @param field 属性
	 * @param val 属性的值
	 * @param row 行
	 */
	public void cosmopolisContent(Field field, String val, Row row){
		try{
			
			 //1.判断是否为空
		     if(!org.springframework.util.StringUtils.hasLength(val)){
		    	 addCell(row, "", indexLine);
		    	 return;
		     }
			//2.需要拿到属性的名称，去找map中是否需要转换
		     Map<String, String> fieldValues = attribute.get(field.getName());
		     if(fieldValues != null && fieldValues.size() != 0){
		    	 //3.是否需要转换
		    	 String fieldValue = fieldValues.get(val);
		    	 if(org.springframework.util.StringUtils.hasLength(fieldValue)){
		    		 addCell(row, fieldValue, indexLine);
		    		 return;
		    	 }
		     }
		     addCell(row, val, indexLine);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 国际化header方法
	 */
	public void cosmopolisHeader(){
		Object obj = null;
		if(exportDateList !=null && exportDateList.size() != 0){
			obj = exportDateList.get(0);
		}else{
			return;
		}
		Class<? extends Object> clazz = obj.getClass();
		//拿到所有的属性
		Field[] fields = clazz.getDeclaredFields();
		int headerLength = 0;
		if (clazz.getName() == "com.audit.bean.VisitorAliasSet" || clazz.getName() == "com.audit.bean.AliasSet") {
			//列头的数组
			headerLength = fields.length - 2;//访问者别名导出去掉serialVersionUID、aliasType  //审计对象别名导出去掉serialVersionUID、identcol
		} else if (clazz.getName() == "com.auditsysconfig.bean.AuditBusObj") {
			headerLength = fields.length - 13;
		} else if (clazz.getName() == "com.audit.bean.RuleEdit") {
			headerLength = fields.length - 13;
		}else { 
			headerLength = fields.length;
		}
		
		
	
		//列头的数组
		String[] header = new String[headerLength];
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if (!("RULEEDIT_MAXNUM".equals(name) || "serialVersionUID".equals(name) || "aliasType".equals(name) || "identcol".equals(name)
					//审计对象部分的导出需要屏蔽的字段
					|| "AUDITBUSOBJ_MAXNUM".equals(name)
					|| "orgName".equals(name)
					|| "busType".equals(name)
					|| "busVers".equals(name)
					|| "runEnvir".equals(name)
					|| "devkey".equals(name)
					|| "interdictIpString".equals(name)
					|| "endTime".equals(name)
					|| "ruleWhiteID".equals(name)
					|| "controlOpt".equals(name)
					|| "startTime".equals(name)
					|| "category".equals(name)
					|| "hisManufacturerName".equals(name) 
					//规则管理导出需要屏蔽的字段
					|| "trustIp".equals(name) 
					|| "trustIpToString".equals(name) 
					|| "tableNumLim".equals(name) 
					|| "messageId".equals(name) 
					|| "effectTime".equals(name) 
					|| "query".equals(name) 
					|| "funcWarn".equals(name) 
					|| "relationRule".equals(name) 
					|| "timeRang".equals(name) 
					|| "triggerNum".equals(name) 
					|| "mounthStaticNum".equals(name) 
					)) {
				Map<String, String> values = attribute.get(name);
				if (values != null && values.size() != 0) {
					String attName = values.get("attribute");
					if (attName != null && !attName.trim().equals("")) {
						header[j] = attName;
						j++;
						continue;
					}
				}
				header[j] = name;
				j++;
			}
		}
		this.header = header;
	}
	
	/**
	 * 国际化Sheet的方法
	 */
	public void cosmopolisSheet(){
		Object obj = null;
		if(exportDateList !=null && exportDateList.size() != 0){
			obj = exportDateList.get(0);
		}else{
			return;
		}
		String className = obj.getClass().getSimpleName();
		if(attribute.get(className) != null){
			String cosClassName = attribute.get(className).get("className");
			if(cosClassName != null && !cosClassName.trim().equals("")){
				//说明有国际化别名
				fileName = cosClassName;
			}else{
				fileName = obj.getClass().getName();
			}
		}else{
			fileName = className;
		}
		
	}
	

}
