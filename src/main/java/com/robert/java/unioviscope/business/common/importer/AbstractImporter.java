package com.robert.java.unioviscope.business.common.importer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import com.robert.java.unioviscope.business.common.i18n.I18nMessageResolver;
import com.robert.java.unioviscope.persistence.GenericRepository;

/**
 * Clase abstracta que implementa la interfaz Importer y se encarga de definir
 * el mecanismo de importación independientemente del tipo de entidades que se
 * importan en un determinado fichero.
 * 
 * @author Robert Ene
 * @see com.robert.java.unioviscope.business.common.importer.Importer
 *
 * @param <T>
 *            el tipo de la entidad
 * @param <ID>
 *            el tipo del id de la entidad
 */
public abstract class AbstractImporter<T, ID extends Serializable> implements Importer<T> {

	public static final String NEW_LINE_SEPARATOR = "\n";
	public static final char COMMENT_MARKER = '#';
	public static final String UTF8_BOM = "\uFEFF";

	@Autowired
	private I18nMessageResolver messageResovler;

	private CSVFormat format;

	private List<T> entities;
	private Boolean importError;

	public AbstractImporter() {
		format = CSVFormat.EXCEL.withHeader(getHeaders()).withIgnoreSurroundingSpaces().withIgnoreEmptyLines()
				.withRecordSeparator(NEW_LINE_SEPARATOR).withCommentMarker(COMMENT_MARKER);
	}

	@Override
	public OutputStream importFile(InputStream in) throws IOException {
		setImportError(false);
		entities = new ArrayList<>();

		OutputStream out = new ByteArrayOutputStream();

		CSVParser parser = new CSVParser(new BufferedReader(new InputStreamReader(in)), format);
		CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(out)), format);

		List<CSVRecord> records = parser.getRecords();
		validateHeader(records.get(0), printer);

		for (int i = 1; i < records.size(); i++) {
			CSVRecord record = records.get(i);
			printer.printRecord(record);

			if (record.isConsistent()) {
				processRecord(record, printer);
			} else {
				setImportError(true);
				printer.printComment(messageResovler
						.getMessage("import.columns.size.invalid", record.size(), getHeaders().length));
			}
		}

		saveEntities();

		printer.flush();
		printer.close();
		in.close();
		parser.close();
		out.flush();
		out.close();

		return (isImportError()) ? out : new ByteArrayOutputStream();
	}

	/**
	 * Método que devuelve el servicio de internacionalización de los mensajes
	 * de error.
	 * 
	 * @return el servicio de internacionalización.
	 */
	public I18nMessageResolver getMessageResolver() {
		return messageResovler;
	}

	/**
	 * Método que añade una entidad a la lista de entidades durante el
	 * procesamiento del fichero de importación, para posteriormente guardarla
	 * en el sistema.
	 * 
	 * @param entity
	 *            la entidad a añadir a la lista.
	 */
	public void addEntity(T entity) {
		entities.add(entity);
	}

	/**
	 * Método que devuelve la lista de entidades acumuladas durante el
	 * procesamiento del fichero de importación.
	 * 
	 * @return la lista de entidades.
	 */
	public List<T> getEntities() {
		return entities;
	}

	/**
	 * Método que devuelve el estado de error durante el procesamiento del
	 * fichero de importación.
	 * 
	 * @return true si hubo algún error durante la importación del fichero,
	 *         false de lo contrario.
	 */
	public Boolean isImportError() {
		return importError;
	}

	/**
	 * Método que modifica el estado de error durante el procesamiento del
	 * fichero de importación.
	 * 
	 * @param importError
	 *            el estado de error.
	 */
	public void setImportError(Boolean importError) {
		this.importError = importError;
	}

	/**
	 * Método abstracto que devuelve la cabecera de un fichero de importación
	 * perteneciente a una entidad concreta.
	 * 
	 * @return array que contiene la cabecera del fichero.
	 */
	public abstract String[] getHeaders();

	/**
	 * Método abstracto que define el mecanismo de procesamiento de una fila de
	 * a un fichero de importación perteneciente a una entidad concreta.
	 * 
	 * @param record
	 *            la fila a procesar.
	 * @param printer
	 *            el fichero en el que se guardan los errores detectados.
	 */
	public abstract void processRecord(CSVRecord record, CSVPrinter printer) throws IOException;

	/**
	 * Método que devuelve el tipo concreto del repositorio para una determinada
	 * entidad durante el procesamiento del fichero de importación.
	 * 
	 * @return el tipo concreto de repositorio de la entidad.
	 */
	public abstract GenericRepository<T, ID> getRepository();

	private void validateHeader(CSVRecord header, CSVPrinter printer) throws IOException {
		if (!header.isConsistent()) {
			setImportError(true);
			printer.printComment(
					messageResovler.getMessage("import.headers.size.invalid", header.size(), getHeaders().length));
		} else {
			for (int i = 0; i < getHeaders().length; i++) {
				if (!ignoreBom(header.get(i)).equalsIgnoreCase(getHeaders()[i])) {
					setImportError(true);
					printer.printComment(messageResovler.getMessage("import.headers.invalid", extractHeaders(header),
							String.join(",", getHeaders())));
					break;
				}
			}
		}
	}

	private String extractHeaders(CSVRecord header) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < header.size(); i++) {
			sb.append(header.get(i).trim());
			if (i != header.size() - 1)
				sb.append(",");
		}

		return sb.toString().replaceAll("(?i)" + UTF8_BOM, "");
	}

	private String ignoreBom(String string) {
		return string.replaceAll("(?i)" + UTF8_BOM, "");
	}

	private void saveEntities() {
		if (!isImportError()) {
			getEntities().forEach(e -> getRepository().save(e));
		}
	}
}
