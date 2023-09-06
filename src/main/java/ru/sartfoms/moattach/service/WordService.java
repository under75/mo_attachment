package ru.sartfoms.moattach.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.entity.Dudl;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.entity.Person;
import ru.sartfoms.moattach.entity.Policy;
import ru.sartfoms.moattach.entity.User;
import ru.sartfoms.moattach.model.AttachFormParameters;

@Service
public class WordService {
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("«dd» MMMM yyyy");
	private static final int GENERIC_FONT = 9;
	private static final int AUXILIARY_FONT = 7;

	public InputStream createDoc(User user, AttachFormParameters formParams, String addrRgStr, String addrPrStr,
			String addrMoStr, LocalDate addrDateB, Person person, Policy policy, Dudl dudl, Lpu lpu, MedMz doctor)
			throws IOException {

		try (XWPFDocument doc = new XWPFDocument()) {

			XWPFParagraph p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.RIGHT);
			XWPFRun r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Руководителю медицинской организации");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(lpu.getName());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("(наименование МО)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(lpu.getAddress());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("(фактический адрес МО)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(formParams.getChiefDoc());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("(фамилия и инициалы руководителя МО)");

			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.CENTER);
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Заявление №_______ о выборе медицинской организации");

			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.LEFT);
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("	Я,");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);

			if (formParams.getDudlPredst() == null) {
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(" " + person.getLastName() + " " + person.getFirstName() + " " + person.getPatronymic());
				r.addBreak();
				r = p.createRun();
				r.setFontSize(AUXILIARY_FONT);
				r.setText("		(Ф., И., О(при наличии)");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("дата рождения");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(" " + person.getBirthDay().format(FORMATTER));
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText(", место рождения");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				if (dudl.getBirthPlace() != null) {
					r.setItalic(true);
					r.setUnderline(UnderlinePatterns.SINGLE);
					r.setText(" " + dudl.getBirthPlace());
				} else {
					r.setText("_____________________________________________________________________");
				}
				r.addBreak();
				r = p.createRun();
				r.setFontSize(AUXILIARY_FONT);
				r.setText("		(число, месяц, год)");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("гражданство");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(" " + (dudl.getCtznOksm() != null ? dudl.getCtznOksm().getName2() : ""));
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				int gender = person.getGender().intValue();
				r.setText(", пол ");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(gender == 1 ? "мужской" : gender == 2 ? "женский" : "не определен");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("прошу прикрепить меня для оказания первичной медико-санитарной помощи к");
			} else {
				r.setText("_____________________________________________________________________");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(AUXILIARY_FONT);
				r.setText("		(Ф., И., О(при наличии)");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("прошу прикрепить гражданина");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(" " + person.getLastName() + " " + person.getFirstName() + " " + person.getPatronymic());
				r.addBreak();
				r = p.createRun();
				r.setFontSize(AUXILIARY_FONT);
				r.setText("		(Ф.И.О полностью)");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("дата рождения");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(" " + person.getBirthDay().format(FORMATTER));
				int gender = person.getGender().intValue();
				r.setText(", пол ");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(gender == 1 ? "мужской" : gender == 2 ? "женский" : "не определен");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("законным представителем которого я являюсь: ");
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText("несовершеннолетний ребенок");
				r.addBreak();
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("для оказания первичной медико-санитарной помощи к");
			}
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(lpu.getFullName());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		(полное наименование медицинской организации)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Полис обязательного медицинского страхования (временное свидетельство)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("№ ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(policy.getEnp() != null ? policy.getEnp() : policy.getPcySer() + " " + policy.getPcyNum());
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(", выдан страховой медицинской организацией ");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(policy.getInsurfName() + " ");
			r.setText(policy.getPcyDateB().format(FORMATTER2));
			r.setText(" года");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("СНИЛС (при наличии) ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(formParams.getSnils());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Место регистрации: ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(addrRgStr);
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(", дата регистрации ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			if (addrDateB != null) {
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(addrDateB.format(FORMATTER));
			} else {
				r.setText("________");
			}
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Место жительства(пребывания): ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(addrPrStr);
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText(
					"(адрес для оказания медицинской помощи на дому при вызове медицинского работника, указывается в случае адреса, отличного от адреса места регистрации)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Прикреплен к медицинской организации на момент подачи заявления");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			if (!formParams.getMoName().isEmpty()) {
				r.setItalic(true);
				r.setUnderline(UnderlinePatterns.SINGLE);
				r.setText(formParams.getMoName() + ", " + addrMoStr);
			} else {
				r.setText("______________________________________________________________");
			}
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		(наименование, фактический адрес)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			if (formParams.getMoName().isEmpty()) {
				r.setUnderline(UnderlinePatterns.SINGLE);
			}
			r.setText("Не прикреплен");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(" к медицинской организации (подчеркнуть, если не прикреплен к медицинской организации).");

			r.addBreak();

			if (formParams.getDudlPredst() == null) {
				r.setText("Паспорт(другой документ, удостоверяющий личность): серия ");
			} else {
				r.setText("Паспорт (другой документ, удостоверяющий личность прикрепляющегося гражданина):");
				r.addBreak();
				r.setText("серия ");
			}
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(dudl.getDudlSer());
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(" № ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(dudl.getDudlNum());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("выдан ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(dudl.getDudlDateB().format(FORMATTER2));
			r.setText(" года");
			r.addBreak();
			if (dudl.getIssuer() != null) {
				r.setText(dudl.getIssuer());
			} else {
				r = p.createRun();
				r.setFontSize(GENERIC_FONT);
				r.setText("______________________________________________________________");
			}
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		(наименование органа, выдавшего документ)");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Контактная информация ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(formParams.getPhone().isEmpty() ? "" : "тел.: " + formParams.getPhone() + ",   ");
			r.setText(formParams.getEmail().isEmpty() ? "" : "email: " + formParams.getEmail());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(
					"	Настоящим подтверждаю выбор Вашей медицинской организации для получения первичной медико-санитарной помощи и согласие на использование моих персональных данных при обработке в соответствии с действующим законодательством Российской Федерации.");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("«___»_____________20___года ________/_____________");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		подпись/расшифровка подписи");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("Дата и время регистрации заявления: ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(LocalDate.parse(formParams.getRegDate()).format(FORMATTER2) + " года");
			p = doc.createParagraph();
			p.setAlignment(ParagraphAlignment.LEFT);
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("РЕШЕНИЕ РУКОВОДИТЕЛЯ МЕДИЦИНСКОЙ ОРГАНИЗАЦИИ:");
			r.addBreak();
			r.setText("Прикрепить с ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(LocalDate.parse(formParams.getEffDate()).format(FORMATTER2));
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(" года. Участок № ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(formParams.getLpuUnit());
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(" Врач ");
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setItalic(true);
			r.setUnderline(UnderlinePatterns.SINGLE);
			r.setText(doctor.getLastName() + " " + doctor.getFirstName() + " " + doctor.getPatronymic());
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText(
					"Отказать в прикреплении в связи с _____________________________________________________________________");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("«___»_____________20___года ________/_____________");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		подпись/расшифровка подписи");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(GENERIC_FONT);
			r.setText("	М.П.");
			r.addBreak();
			r.setText(
					"По требованию заявителя копия заявления с решением руководителя медицинской организации выдана на руки ");
			r.addBreak();
			r.setText("«___»_____________20___года");
			r.addBreak();
			r.setText("Получил копию заявления: «___»________20___года ________/_____________");
			r.addBreak();
			r = p.createRun();
			r.setFontSize(AUXILIARY_FONT);
			r.setText("		подпись/расшифровка подписи");

			ByteArrayOutputStream b = new ByteArrayOutputStream();
			doc.write(b);
			return new ByteArrayInputStream(b.toByteArray());
		}

	}

}
