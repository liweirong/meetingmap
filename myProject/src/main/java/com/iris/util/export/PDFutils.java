package com.iris.util.export;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.ibm.icu.text.SimpleDateFormat;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

public abstract class PdfUltity {

	BaseFont base;  //基础字体
	BaseFont baseContent; //仿宋基础字体
	Font fontContent;  //正文字体样式
	Font fontTitle;  //标题字体样式
	Font fontSources;//正文内容样式
	private Font fontHeader;  //表格栏字体样式
	
	Document document;
	ByteArrayOutputStream out;
	PdfPTable table;
	private int startNum;
	private int maxNum;
	

	private HttpServletResponse response;
	private int COLUMN;


	void setConfiguration(String fileName) throws UnsupportedEncodingException {
		response.setContentType("application/pdf");
		// response.setContentType("application/ms-excel" );

		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String( fileName.getBytes("gb2312"), "ISO8859-1" )  + ".pdf");
	}


	void setTitle(String title,String Sources) throws DocumentException {

		String context = title; // 文档内容
		fontTitle = new Font(base, 16, Font.BOLD, Color.black);
		
		Paragraph paragraph = new Paragraph(context, fontTitle); // 创建段落，并设置字体

		paragraph.setAlignment(Element.ALIGN_CENTER); // 设置段落居中

		document.add(paragraph); // 将段落添加到文档中
		
		fontSources = new Font(base, 12, Font.NORMAL, Color.black);
		
		paragraph = new Paragraph(Sources, fontSources); // 创建内容
		
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		document.add(paragraph); // 将内容添加到
		
		Font dateFont=new Font(base, 9, Font.NORMAL, Color.black);  //时间
		
		paragraph = new Paragraph("制表日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), dateFont); 

		paragraph.setAlignment(Element.ALIGN_RIGHT);
		
		paragraph.setSpacingBefore(10f);
		document.add(paragraph);
	}
	
	void setTitle(String title) throws DocumentException {

		String context = title; // 文档内容
		fontTitle = new Font(base, 15, Font.BOLD, Color.black);
		
		Paragraph paragraph = new Paragraph(context, fontTitle); // 创建段落，并设置字体

		paragraph.setAlignment(Element.ALIGN_CENTER); // 设置段落居中

		document.add(paragraph); // 将段落添加到文档中
		
		Font dateFont=new Font(base, 6, Font.NORMAL, Color.black);  //时间
		
		paragraph = new Paragraph(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), dateFont); // 创建段落，并设置字体

		paragraph.setAlignment(Element.ALIGN_CENTER);
		
		paragraph.setSpacingBefore(10f);
		document.add(paragraph);
	}

	void setHeader(String[] headers) throws Exception {

		float[] wd = new float[headers.length];  //列宽占的百分比
		for (int i = 0; i < wd.length; i++) {
			wd[i] = ((float)1)/headers.length;
		}

		table.setWidths(wd);
		table.setSpacingBefore(20f);
		table.setWidthPercentage(100); //占整个页面
	

		for (String string : headers) {
			PdfPCell c1;
			Paragraph par = new Paragraph(string, fontHeader);
			c1 = new PdfPCell(par);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setBackgroundColor(Color.yellow);
			c1.setNoWrap(false);
			table.addCell(c1);
		}

	}

	public abstract void setContent()
			throws Exception;

	public PdfUltity(HttpServletResponse response) {
		this.response = response;

		try {
			Rectangle rectPageSize = new Rectangle(PageSize.A4);
			document = new Document(rectPageSize.rotate(), 5, 5, 20, 5);

			//baseChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				//	BaseFont.NOT_EMBEDDED);
			base=BaseFont.createFont("/MSYH.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); 
			baseContent=BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); 
			fontContent = new Font(baseContent, 8, Font.NORMAL, Color.black);
			fontTitle = new Font(base, 20, Font.BOLD, Color.black);
			fontHeader=new Font(base, 10, Font.NORMAL, Color.black);
			fontHeader.setColor(Color.gray);
			
			out = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.open();

	}
	
	/**
	 * 带分表的构造方法
	 * @param response
	 * @param startNum
	 * @param maxNum
	 */
	public PdfUltity(HttpServletResponse response, int startNum, int maxNum) {
		this.response = response;

		try {
			Rectangle rectPageSize = new Rectangle(PageSize.A4);
			document = new Document(rectPageSize.rotate(), 5, 5, 20, 5);

			//baseChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				//	BaseFont.NOT_EMBEDDED);
			base=BaseFont.createFont("/MSYH.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); 
			baseContent=BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED); 
			fontContent = new Font(baseContent, 8, Font.NORMAL, Color.black);
			fontTitle = new Font(base, 20, Font.BOLD, Color.black);
			fontHeader=new Font(base, 10, Font.NORMAL, Color.black);
			fontHeader.setColor(Color.gray);	
			out = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, out);
			this.startNum = startNum;
			this.maxNum = maxNum;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.open();
	}

	public void export(String fileName, String title,String sources, String[] header) {
		try {
			this.COLUMN = header.length;
			table = new PdfPTable(COLUMN);
			setConfiguration(fileName);
			setTitle( title,sources);
			setHeader(header);
			setContent();
			releaseResource();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void export(String fileName, String title, String[] header) {
		try {
			this.COLUMN = header.length;
			table = new PdfPTable(COLUMN);
			setConfiguration(fileName);
			setTitle( title);
			setHeader(header);
			setContent();
			releaseResource();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportZIP(String fileName, String title, String sources, String[] header, String filePath) {
		try {
			this.COLUMN = header.length;
			table = new PdfPTable(COLUMN);
			setConfigurationZIP(fileName);
			setTitle( title,sources);
			setHeader(header);
			setContent();
			handleLastFile(filePath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setConfigurationZIP(String fileName) throws UnsupportedEncodingException {
		//response.setContentType("ms-excel");
//		response.setContentType("application/ms-excel" );
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + ".zip");
	
	}

	private void handleLastFile(String path) throws Exception {
		
		//在指定目录生成文件
		File file = new File(path + ".pdf");
		FileOutputStream fos = new FileOutputStream(file);
		document.add(table);
		document.close();
		out.writeTo(fos);
		fos.flush();
		fos.close();
		out.close();
		
	}

	public void releaseResource() throws Exception {
		document.add(table);
		document.close();
		OutputStream outputStream = response.getOutputStream();
		out.writeTo(outputStream);
		outputStream.flush();
		outputStream.close();
		out.close();
	}
	
	// 增加一个单元格
	public  void addCell(String label)
			throws BadElementException {
		PdfPCell c1;
		Paragraph par = new Paragraph(label, fontContent);
		c1 = new PdfPCell(par);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setNoWrap(false);
		table.addCell(c1);
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
	

}
