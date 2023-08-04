package ru.sartfoms.moattach.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;

import ru.sartfoms.moattach.dao.AttachOtherRegionsDao;
import ru.sartfoms.moattach.entity.AttachOtherRegions;
import ru.sartfoms.moattach.entity.DudlType;
import ru.sartfoms.moattach.entity.Lpu;
import ru.sartfoms.moattach.entity.MedMz;
import ru.sartfoms.moattach.exception.ExcelGeneratorException;
import ru.sartfoms.moattach.model.AttachFormParameters;
import ru.sartfoms.moattach.util.ExcelGenerator;

@Service
public class ExcelService {
	private final LpuService lpuService;
	private final MedMzService medMzService;
	private final DudlTypeService dudlTypeService;
	private final AddressService addressService;
	private final AttachOtherRegionsDao attachOtherRegionsDao;

	public ExcelService(AttachOtherRegionsDao attachOtherRegionsDao, MedMzService medMzService, LpuService lpuService,
			DudlTypeService dudlTypeService, AddressService addressService) {
		this.lpuService = lpuService;
		this.medMzService = medMzService;
		this.dudlTypeService = dudlTypeService;
		this.addressService = addressService;
		this.attachOtherRegionsDao = attachOtherRegionsDao;
	}

	public InputStream createExcel(AttachFormParameters formParams) throws IOException, ExcelGeneratorException {
		LocalDate effDateMin = formParams.getEffDate().isEmpty() ? null : LocalDate.parse(formParams.getEffDate());
		LocalDate effDateMax = formParams.getExpDate().isEmpty() ? null : LocalDate.parse(formParams.getExpDate());

		Lpu lpu = lpuService.getById(formParams.getLpuId());
		Collection<MedMz> doctors = medMzService.findByLpuId(lpu.getParentId() != 0 ? lpu.getParentId() : lpu.getId());
		Collection<DudlType> dudlTypes = dudlTypeService.findAll();

		return new ExcelGenerator(attachOtherRegionsDao.findByParams(formParams.getLpuId(), formParams.getLpuUnit(),
				formParams.getDoctorSnils(), effDateMin, effDateMax)) {

			@SuppressWarnings("unchecked")
			@Override
			public ExcelGenerator createBody() {
				Font bodyFont = template.createFont();
				bodyFont.setFontName("Calibri");
				CellStyle bodyStyleAlignmentLeft = template.createCellStyle();
				bodyStyleAlignmentLeft.setFont(bodyFont);
				bodyStyleAlignmentLeft.setAlignment(HorizontalAlignment.LEFT);
				bodyStyleAlignmentLeft.setBorderRight(BorderStyle.DASHED);

				CellStyle bodyStyleAlignmentCenter = template.createCellStyle();
				bodyStyleAlignmentCenter.setFont(bodyFont);
				bodyStyleAlignmentCenter.setAlignment(HorizontalAlignment.CENTER);
				bodyStyleAlignmentCenter.setBorderRight(BorderStyle.DASHED);

				int rowNum = 3;// firstDataRow
				for (AttachOtherRegions rowData : (Collection<AttachOtherRegions>) rows) {
					int column = 0;
					XSSFRow row = sheet.createRow(rowNum++);
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
							String.valueOf(rowNum - 3));
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getLastName());
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getFirstName());
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getPatronymic());
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
							rowData.getBirthDay() != null ? rowData.getBirthDay().format(DATE_FORMATTER) : "");
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
							rowData.getGender().intValue() == 1 ? "М"
									: rowData.getGender().intValue() == 2 ? "Ж" : "?");
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentCenter),
							(rowData.getPcySer() != null ? rowData.getPcySer() + " " : "") + rowData.getPcyNum());
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
							(dudlTypes.stream().filter(t -> t.getDocCode().equals(rowData.getDudlType())).count() > 0
									? dudlTypes.stream().filter(t -> t.getDocCode().equals(rowData.getDudlType()))
											.findAny().get().getDocName()
									: "") + (rowData.getDudlSer() != null ? " " + rowData.getDudlSer() : "")
									+ (rowData.getDudlNum() != null ? " " + rowData.getDudlNum() : ""));
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
							(rowData.getPhone() != null ? "тел.: " + rowData.getPhone() : "")
									+ (rowData.getEmail() != null
											? (rowData.getPhone() != null ? ", " : "") + "email: " + rowData.getEmail()
											: ""));
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
							addressService.getAddrStr(rowData.getAoguidpr(), rowData.getHsguidpr()));
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft), rowData.getLpuUnit());
					setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
							doctors.stream().filter(t -> t.getSnils().equals(rowData.getDoctorsnils()))
									.map(t -> t.getLastName() + " " + t.getFirstName() + " " + t.getPatronymic())
									.findAny().get());
					if (rowData.getExpDate() != null) {
						setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
								rowData.getEffDate().format(DATE_FORMATTER) + " - "
										+ rowData.getExpDate().format(DATE_FORMATTER));
					} else {
						setCellValue(createCellAndFormat(row, column++, bodyStyleAlignmentLeft),
								rowData.getEffDate().format(DATE_FORMATTER) + " - "
										+ rowData.getEffDate().plusDays(rowData.getPeriod()).format(DATE_FORMATTER));
					}
				}
				for (int i = 0; i < columns.length; i++) {
					sheet.autoSizeColumn(i);
				}

				return this;
			}

			@Override
			protected ExcelGenerator createHeader() {
				CellStyle titleStyle = template.createCellStyle();
				Font titleFont = template.createFont();
				titleFont.setFontName("Calibri");
				titleFont.setItalic(true);
				titleStyle.setFont(titleFont);

				CellStyle headerStyle = template.createCellStyle();
				Font headerFont = template.createFont();
				headerFont.setBold(true);
				headerFont.setFontName("Calibri");
				headerStyle.setFont(headerFont);
				headerStyle.setWrapText(true);
				headerStyle.setAlignment(HorizontalAlignment.CENTER);
				headerStyle.setBorderBottom(BorderStyle.DASHED);
				headerStyle.setBorderRight(BorderStyle.DASHED);
				headerStyle.setBorderTop(BorderStyle.DASHED);

				columns = new String[] { "№", "Фамилия", "Имя", "Отчество", "Дата рожд.", "Пол", "Полис", "ДУдЛ",
						"Контакт", "Адрес проживания", "Участок", "ФИО доктора", "Период" };

				XSSFRow row0 = sheet.createRow(0);
				setCellValue(createCellAndFormat(row0, 0, titleStyle),
						"Учетное прикрепление к МО лиц, застрахованных по ОМС за пределами Саратовской области");
				XSSFRow row1 = sheet.createRow(1);
				setCellValue(createCellAndFormat(row1, 0, titleStyle), lpu.getName());
				XSSFRow row2 = sheet.createRow(2);
				for (int cn = 0; cn < columns.length; cn++) {
					setCellValue(createCellAndFormat(row2, cn, headerStyle), columns[cn]);
				}

				sheet.addMergedRegion(new CellRangeAddress(row0.getRowNum(), row0.getRowNum(), row0.getFirstCellNum(),
						columns.length - 1));
				sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), row1.getFirstCellNum(),
						columns.length - 1));

				return this;
			}
		}.createHeader().createBody().toExcel();
	}
}
