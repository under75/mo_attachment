package ru.sartfoms.moattach.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.AttachOtherRegions;

@Service
public class WordService {

	public InputStream createDoc(AttachOtherRegions attachOtherRegions) throws IOException {

		try (XWPFDocument doc = new XWPFDocument()) {

			XWPFParagraph p1 = doc.createParagraph();
			p1.setAlignment(ParagraphAlignment.RIGHT);
			XWPFRun r1 = p1.createRun();
			r1.setFontSize(8);
			r1.setText("Руководителю медицинской организации");
			r1.addBreak();
			r1.setText("____________________________________");
			r1.addBreak();
			r1.setText("____________________________________");
			r1.addBreak();
			r1.setText("____________________________________");
			r1.addBreak();
			r1.setText("(Ф.И.О. полностью)		");
			
			XWPFParagraph p2 = doc.createParagraph();
			p2.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r2 = p2.createRun();
			r2.setFontSize(8);
			r2.setText("Заявление №_______ о выборе медицинской организации");
			r2.addBreak();

			XWPFParagraph p3 = doc.createParagraph();
			p3.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun r3 = p3.createRun();
			r3.setFontSize(8);
			r3.setUnderline(UnderlinePatterns.SINGLE);
			r3.setText("	Я," + attachOtherRegions.getLastName());
			r3.setFontFamily("Arial");

			XWPFTable table = doc.createTable();
			// Creating first Row
			XWPFTableRow row1 = table.getRow(0);
			row1.getCell(0).setText("Java, Scala");
			row1.addNewTableCell().setText("PHP, Flask");
			row1.addNewTableCell().setText("Ruby, Rails");

			// Creating second Row
			XWPFTableRow row2 = table.createRow();
			row2.getCell(0).setText("C, C ++");
			row2.getCell(1).setText("Python, Kotlin");
			row2.getCell(2).setText("Android, React");

			// add png image
			XWPFRun r4 = doc.createParagraph().createRun();
			r4.addBreak();

			ByteArrayOutputStream b = new ByteArrayOutputStream();
			doc.write(b);
			return new ByteArrayInputStream(b.toByteArray());
		}

	}

}
