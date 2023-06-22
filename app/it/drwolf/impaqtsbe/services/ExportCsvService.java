package it.drwolf.impaqtsbe.services;

import it.drwolf.impaqtsbe.dto.FrequencyResultLine;
import it.drwolf.impaqtsbe.dto.KWICLine;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportCsvService {
	static final String FREQUENCY = "frequency";
	static final String REL = "rel[%]";
	static final String ELEMENT = "Element";
	static final String OVERALL_FREQ = "Overall frequency";
	static final String WORD = "word";
	static final String LEMMA = "lemma";
	static final String TAG = "tag";

	static final String LEFT = "Left";
	static final String KWIC = "Kwich";
	static final String RIGHT = "Right";

	private String convertToCSV(String[] data) {
		return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(";;"));
	}

	private void elaborateMetadataFrequencyCsv(QueryResponse queryResponse, String filePathStr)
			throws FileNotFoundException {
		List<String[]> strList = new ArrayList<>();
		String[] header = new String[] { queryResponse.getFrequency().getHead(), ExportCsvService.FREQUENCY,
				ExportCsvService.REL,
				String.format("%s: %d - %s: %d", ExportCsvService.ELEMENT, queryResponse.getFrequency().getTotal(),
						ExportCsvService.OVERALL_FREQ, queryResponse.getFrequency().getTotalFreq()) };
		strList.add(header);
		for (FrequencyResultLine frl : queryResponse.getFrequency().getItems()) {
			String[] line = new String[] { String.join(",", frl.getWord()), "" + frl.getFreq(), "" + frl.getRel() };
			strList.add(line);
		}
		File csvOutputFile = new File(filePathStr);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			strList.stream().map(this::convertToCSV).forEach(pw::println);
			pw.flush();
		}
	}

	private void elaborateMultilevelFrequencyCsv(QueryResponse queryResponse, String filePathStr)
			throws FileNotFoundException {
		List<String[]> strList = new ArrayList<>();
		List<String> headerStrListNormalized = new ArrayList<>();
		List<String> headerStrList = List.of(queryResponse.getFrequency().getHead().split(" "));
		headerStrList.stream().forEach(h -> {
			if (h.contains(ExportCsvService.WORD)) {
				headerStrListNormalized.add(ExportCsvService.WORD);
			} else if (h.contains(ExportCsvService.LEMMA)) {
				headerStrListNormalized.add(ExportCsvService.LEMMA);
			} else if (h.contains(ExportCsvService.TAG)) {
				headerStrListNormalized.add(ExportCsvService.TAG);
			}
		});
		String[] header = ArrayUtils.addAll(headerStrListNormalized.toArray(new String[headerStrListNormalized.size()]),
				new String[] { ExportCsvService.FREQUENCY, String.format("%s: %d - %s: %d", ExportCsvService.ELEMENT,
						queryResponse.getFrequency().getTotal(), ExportCsvService.OVERALL_FREQ,
						queryResponse.getFrequency().getTotalFreq()) });
		strList.add(header);
		for (FrequencyResultLine frl : queryResponse.getFrequency().getItems()) {
			String[] line = ArrayUtils.addAll(frl.getWord().toArray(new String[frl.getWord().size()]),
					new String[] { "" + frl.getFreq() });
			strList.add(line);
		}
		File csvOutputFile = new File(filePathStr);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			strList.stream().map(this::convertToCSV).forEach(pw::println);
			pw.flush();
		}
	}

	private void elaborateTextualQueryRequestCsv(QueryResponse queryResponse, String filePathStr)
			throws FileNotFoundException {
		List<String[]> strList = new ArrayList<>();
		String[] header = new String[] { ExportCsvService.LEFT, ExportCsvService.KWIC, ExportCsvService.RIGHT };
		strList.add(header);
		int i = 0;
		for (KWICLine kwic : queryResponse.getKwicLines()) {
			String[] line = new String[] { kwic.getLeftContext().get(0), kwic.getKwic(),
					kwic.getRightContext().get(0) };
			strList.add(line);
			if (i == 105) {
				System.out.println("");
			}
			i++;
		}
		File csvOutputFile = new File(filePathStr);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			strList.stream().map(this::convertToCSV).forEach(pw::println);
			pw.flush();
		}
	}

	private String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	public void storageTmpFileCsvFromQueryResponse(QueryResponse queryResponse, QueryRequest.RequestType requestType,
			String filePathStr) throws FileNotFoundException {

		switch (requestType) {
		case METADATA_FREQUENCY_QUERY_REQUEST:
			this.elaborateMetadataFrequencyCsv(queryResponse, filePathStr);
			break;
		case MULTI_FREQUENCY_QUERY_REQUEST:
			this.elaborateMultilevelFrequencyCsv(queryResponse, filePathStr);
			break;
		case TEXTUAL_QUERY_REQUEST:
			this.elaborateTextualQueryRequestCsv(queryResponse, filePathStr);
			break;
		default:
			break;
		}
	}

}
