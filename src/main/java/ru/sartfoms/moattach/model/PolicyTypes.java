package ru.sartfoms.moattach.model;

import java.util.HashMap;

public class PolicyTypes extends HashMap<String, String> {
	private static final long serialVersionUID = 1L;
	private static PolicyTypes instance;

	private PolicyTypes() {
		this.put("С", "Полис ОМС старого образца");
		this.put("В", "Временное свидетельство в форме бумажного бланка");
		this.put("Е", "Временное свидетельство в форме электронного документа");
		this.put("П", "Бумажный полис ОМС единого образца");
		this.put("Э", "Электронный полис ОМС единого образца");
		this.put("К", "Полис ОМС в составе универсальной электронной карты");
		this.put("Ц", "Цифровой полис ОМС");
		this.put("Х", "Состояние на учёте без полиса ОМС");
		this.put("М", "Состояние на учёте МФЦ");
	}
	
	public static PolicyTypes getInstance() {
        if (instance == null) {
            instance = new PolicyTypes();
        }
        return instance;
    }
}
