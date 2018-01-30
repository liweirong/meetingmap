package export;


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import com.audit.bean.AuditRecord;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;

public class CreatWordUtils {
	private Document doc;
	private ByteArrayOutputStream baos ;
	private String title;
	private RtfFont titleFont ;
	private RtfFont contextFont;
	private Table  table;
//	private String fileName;
//	
//	
//	public String getFileName() {
//		return fileName;
//	}
//	public void setFileName(String fileName)  {
//		this.fileName = fileName;
//	}

	/**
	 * 初始化成员变量
	 */
	public void init(){
		/** 创建Document对象（word文档）  */
		 try {
			doc = new Document(PageSize.A4);
			 
				/** 新建字节数组输出流 */
				 baos = new ByteArrayOutputStream();
				 
				/** 建立一个书写器与document对象关联，通过书写器可以将文档写入到输出流中  */
				RtfWriter2.getInstance(doc, baos);
				doc.open();
				
				/** 标题字体  */
				titleFont = new RtfFont("仿宋_GB2312", 16, Font.NORMAL,
						Color.BLACK);
				/** 正文字体  */
				contextFont = new RtfFont("仿宋_GB2312", 10, Font.NORMAL,
						Color.BLACK);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CreatWordUtils(){	
		init();
	}
	
	/**
	 * 创建有标题的word文档
	 * @param title  标题
	 */
	public CreatWordUtils(String title){
		init();
		
		try {
			/** 第一行（标题）  */
			Paragraph para = new Paragraph(title,contextFont);
			// 设置标题格式对其方式
			para.setAlignment(Element.ALIGN_CENTER);
			//	para.setFont(titleFont);
			doc.add(para);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 创建table
	 * @param columns  列数
	 * @param rows  行数
	 */
	public void createTable(int columns, int rows){
		try {
			/** 表格设置  */
			table = new Table(columns, rows);
//			int[] withs = {20,81};
//			/** 设置每列所占比例  */
//			table.setWidths(withs);
//			/** 表格所占页面宽度  */
//			table.setWidth(100);
			/** 居中显示  */
			table.setAlignment(Element.ALIGN_CENTER);
			/** 自动填满  */
			table.setAutoFillEmptyCells(true);
			
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 填充表格
	 * @param chineseArray  表头中文数据
	 * @param englishArray   英文数据
	 * @param exportRows     填写的内容
	 */
	public void writeTable(String[] chineseArray, String[] englishArray,List<AuditRecord> exportRows){
		try {
			/** 第0行 表头 */
			for (int i = 0; i < chineseArray.length; i++) {
				Cell cell0 = new Cell(
						new Phrase(chineseArray[i], contextFont));
				cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell0);
			}
			
			/**
			 * 表的内容
			 */
			for (int i = 0; i < exportRows.size(); i++) {
				int n=0;
				for (String string : englishArray) {
					Cell cell0 = new Cell(
							new Phrase(getDataText(exportRows.get(i),string), contextFont));
					cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
				}

			}
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	
	/**
	 * 获取数据
	 * @param record    审计的单条记录
	 * @param condiftion   审计的字段名称
	 * @return   对应字段的值
	 */
	public String getDataText(AuditRecord record,String condiftion){
		Class auditClass=record.getClass();
		Method methods[]=auditClass.getDeclaredMethods();
		String resultString="";
		for (Method methodMeta : methods) {
		if (methodMeta.getName().startsWith("get")) {
	
				if (methodMeta.getName().substring(3).toUpperCase().equals(condiftion.toUpperCase())) {
					try {
						resultString+=(methodMeta.invoke(record,null).toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			
		}
		}
		return resultString;
	}
	
	
	/**
	 * 得到word的输入流
	 * @return  输入流
	 */
	public InputStream getWordStream(){		
		// 得到输入流  
		
		try {
				doc.add(table);
				doc.close();
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
//				
//				String temps="发生报告"+sdf.format(new Date());
//				setFileName(temps) ;
//				fileName = System.currentTimeMillis()+".doc";
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				baos.close();
				return bais;
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
		}
		
	}
}

