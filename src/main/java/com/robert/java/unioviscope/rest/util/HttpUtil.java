package com.robert.java.unioviscope.rest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.robert.java.unioviscope.model.types.ImportType;

/**
 * Clase de utilidades para las respuestas de las peticiones realizadas al sistema.
 *
 * @author Robert Ene
 */
public class HttpUtil {

	public static final String X_FILENAME_HEADER = "X-Filename";
	public static final MediaType IMPORT_CSV_MEDIATYPE = new MediaType("application", "vnd.ms-excel");

	public static final MediaType EXPORT_XLSX_MEDIATYPE = new MediaType("application",
			"vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml");
	public static final String EXPORT_DATE_FORMAT = "dd.MM.yyyy-HH.mm";

	public static HttpHeaders getImportHeaders(Integer fileSize, String fileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(IMPORT_CSV_MEDIATYPE);
		headers.setContentDispositionFormData(HttpHeaders.CONTENT_DISPOSITION, fileName);
		headers.add(X_FILENAME_HEADER, fileName.concat(".").concat(ImportType.CSV.toString().toLowerCase()));
		headers.setContentLength(fileSize);
		return headers;
	}

	public static HttpHeaders getExportHeaders(Integer fileSize, String fileName, String fileFormat) {
		DateFormat dateFormat = new SimpleDateFormat(EXPORT_DATE_FORMAT);
		String now = dateFormat.format(new Date()).toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(EXPORT_XLSX_MEDIATYPE);
		headers.setContentDispositionFormData(HttpHeaders.CONTENT_DISPOSITION, fileName);
		headers.add(X_FILENAME_HEADER, fileName.concat(" - ").concat(now).concat(".").concat(fileFormat.toLowerCase()));
		headers.setContentLength(fileSize);
		return headers;
	}
}
