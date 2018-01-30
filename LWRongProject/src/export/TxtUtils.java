package export;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public abstract class TxtUtils {
	private HttpServletResponse response;

	private String fileName;

	private PrintWriter writer;

	void setConfiguration(String fileName) {
		response.setContentType("application/octet-stream");
		// response.setContentType("application/ms-excel" );

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");

	}

	public abstract void setContent() throws Exception;

	public TxtUtils(HttpServletResponse response) throws IOException {
		this.response = response;

		writer = new PrintWriter(response.getOutputStream(), true);

	}

	public void export(String fileName, String title, String[] header) {
		try {

			this.fileName = fileName;
			setConfiguration(fileName);
			setTitle(title);
			setHeader(header);
			setContent();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				releaseResource();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setHeader(String[] header) {
		// TODO Auto-generated method stub
		for (String string : header) {
			writer.write(string + getTab(10));
		}
		writer.write("\r\n");
	}

	private void setTitle(String title) {
		// TODO Auto-generated method stub
		writer.write(getTab(10) + getTab(10) + title + "\r\n\r\n\r\n");

	}

	protected String getTab(int count) {
		// TODO Auto-generated method stub
		String string = "";
		for (int i = 0; i < count; i++) {
			string += "\t";
		}
		return string;
	}

	public void releaseResource() throws Exception {
		writer.flush();
		writer.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}

	public String getFileName() {
		return fileName;
	}

	public PrintWriter getWriter() {
		return writer;
	}

}
