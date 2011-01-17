package com.mpe.report.tools;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;

public class ListOfJournalHeaderFooter implements PdfPageEvent {

protected PdfPTable table; 
protected PdfPTable footer;
private String reportTitle = "REPORT TITLE";
private String companyName = "COMPANY NAME";
private String fromToDate = "mm-dd-yyyy - mm-dd-yyyy";
int headerWidth = 300;
int headerHeight = 60;
	public ListOfJournalHeaderFooter() {
	
	}
	
	public void createHeaderTable() {
		table = new PdfPTable(1);
		table.setTotalWidth(headerWidth);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cell = new PdfPCell(new Phrase(this.reportTitle,FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(this.companyName,FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(this.fromToDate,FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);		
	}
	
	public void createFooterTable() {
		footer = new PdfPTable(4);
		footer.setTotalWidth(300);
		footer.getDefaultCell()
		.setHorizontalAlignment(Element.ALIGN_CENTER);
		footer.addCell(new Phrase(new Chunk("First Page")
		.setAction(new PdfAction(PdfAction.FIRSTPAGE))));
		footer.addCell(new Phrase(new Chunk("Prev Page")
		.setAction(new PdfAction(PdfAction.PREVPAGE))));
		footer.addCell(new Phrase(new Chunk("Next Page")
		.setAction(new PdfAction(PdfAction.NEXTPAGE))));
		footer.addCell(new Phrase(new Chunk("Last Page")
		.setAction(new PdfAction(PdfAction.LASTPAGE))));		
	}
	public void onOpenDocument(PdfWriter arg0, Document arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onStartPage(PdfWriter arg0, Document arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
//		if (document.getPageNumber() > 1) {
//			ColumnText.showTextAligned(cb,
//			Element.ALIGN_CENTER, header,
//			(document.right() - document.left()) / 2
//			+ document.leftMargin(), document.top() + 10, 0);
//		}
//		System.out.println("document.top() : " + document.top());
//		table.writeSelectedRows(0, -1,
//				(document.right() - document.left() - headerWidth) / 2
//				+ document.leftMargin(), document.top() + headerHeight, cb);
		table.writeSelectedRows(0, -1,
				document.leftMargin(), document.top() + headerHeight, cb);		
	}
	public void onCloseDocument(PdfWriter arg0, Document arg1) {
		// TODO Auto-generated method stub
		
	}
	public void onParagraph(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onParagraphEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onChapter(PdfWriter arg0, Document arg1, float arg2, Paragraph arg3) {
		// TODO Auto-generated method stub
		
	}
	public void onChapterEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onSection(PdfWriter arg0, Document arg1, float arg2, int arg3, Paragraph arg4) {
		// TODO Auto-generated method stub
		
	}
	public void onSectionEnd(PdfWriter arg0, Document arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}
	public void onGenericTag(PdfWriter arg0, Document arg1, Rectangle arg2, String arg3) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the companyName.
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName The companyName to set.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return Returns the fromToDate.
	 */
	public String getFromToDate() {
		return fromToDate;
	}
	/**
	 * @param fromToDate The fromToDate to set.
	 */
	public void setFromToDate(String fromToDate) {
		this.fromToDate = fromToDate;
	}
	/**
	 * @return Returns the reportTitle.
	 */
	public String getReportTitle() {
		return reportTitle;
	}
	/**
	 * @param reportTitle The reportTitle to set.
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	

}
