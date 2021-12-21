package br.com.coodesh.sfn.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

public class Util {
	
	@Value("${request.cronExpression}")
	private static String cronExpression;

	public static String convertCronExpressionToHour() {
		String[] list = cronExpression.split(" ");
		String second = list[0].length() == 1 ? "0" + list[0] : list[0];
		String minute = list[1].length() == 1 ? "0" + list[1] : list[1];
		String hour = list[2].length() == 1 ? "0" + list[2] : list[2];
		return hour + ":" + minute + ":" + second;
	}
	
	public static String getDateToRequest() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime now = LocalDateTime.now().minusDays(1);
		return dtf.format(now).replaceAll("[0-9]{2}[:][0-9]{2}[:][0-9]{2}", convertCronExpressionToHour());
	}

}
