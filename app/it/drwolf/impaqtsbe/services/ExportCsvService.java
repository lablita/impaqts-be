package it.drwolf.impaqtsbe.services;

import com.opencsv.CSVWriter;
import it.drwolf.impaqtsbe.dto.CollocationItem;
import it.drwolf.impaqtsbe.dto.FrequencyResultLine;
import it.drwolf.impaqtsbe.dto.KWICLine;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.dto.WordListItem;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportCsvService {
	static final String FREQUENCY = "frequency";
	static final String REL = "rel[%]";
	static final String ELEMENT = "Element";
	static final String OVERALL_FREQ = "Overall frequency";
	static final String WORD = "word";
	static final String LEMMA = "lemma";
	static final String TAG = "tag";
	static final String LEFT = "Left";
	static final String KWIC = "Kwic";
	static final String RIGHT = "Right";
	static final String COLLOCATION = "collocation";
	static final String CONC_COUNT = "Concurrence count";
	static final String CAND_COUNT = "Candidate count";
	static final String T_SCORE = "T-score";
	static final String MI = "MI";
	static final String MI3 = "MI3";
	static final String LOG_LIKELIHOOD = "log likelihood";
	static final String MIN_SENS = "min. sensitivity";
	static final String LOG_DICE = "logDice";
	static final String MI_LOG_F = "MI.log_f";

	private static final Map<String, String> CAPITALI = Map.of("m", MI, "3", MI3, "l", LOG_LIKELIHOOD, "s", MIN_SENS,
			"d", LOG_DICE, "p", MI_LOG_F);

	private void appendCsv(List<String[]> lines, String filePathStr) throws Exception {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePathStr, true))) {
			writer.writeAll(lines);
		}
	}

	private void elaborateCollocationCsv(QueryResponse queryResponse, String filePathStr, boolean header)
			throws Exception {
		List<String[]> strList = new ArrayList<>();
		if (header) {
			String[] headerStrs = new String[] { "", ExportCsvService.COLLOCATION, ExportCsvService.REL,
					String.format("%s: %d - %s: %d", ExportCsvService.ELEMENT, queryResponse.getCollocations().size(),
							ExportCsvService.OVERALL_FREQ, 5) };
			strList.add(headerStrs);
		}
		for (CollocationItem ci : queryResponse.getCollocations()) {
			String[] line = new String[] {
					String.join(",", ci.getTScore().toString(), ci.getMi().toString(), ci.getLogDice().toString()) };
			strList.add(line);
		}
		this.appendCsv(strList, filePathStr);
	}

	private void elaborateMetadataFrequencyCsv(QueryResponse queryResponse, String filePathStr, boolean header)
			throws Exception {
		List<String[]> strList = new ArrayList<>();
		if (header) {
			String[] headerStrs = new String[] { queryResponse.getFrequency().getHead(), ExportCsvService.FREQUENCY,
					ExportCsvService.REL,
					String.format("%s: %d - %s: %d", ExportCsvService.ELEMENT, queryResponse.getFrequency().getTotal(),
							ExportCsvService.OVERALL_FREQ, queryResponse.getFrequency().getTotalFreq()) };
			strList.add(headerStrs);
		}
		for (FrequencyResultLine frl : queryResponse.getFrequency().getItems()) {
			String[] line = new String[] { String.join(",", frl.getWord()), String.valueOf(frl.getFreq()),
					String.valueOf(frl.getRel()) };
			strList.add(line);
		}
		this.appendCsv(strList, filePathStr);
	}

	private void elaborateMultilevelFrequencyCsv(QueryResponse queryResponse, String filePathStr, boolean header)
			throws Exception {
		List<String[]> strList = new ArrayList<>();
		List<String> headerStrListNormalized = new ArrayList<>();
		List<String> headerStrList = Arrays.asList(queryResponse.getFrequency().getHead().split(" "));
		headerStrList.stream().forEach(h -> {
			if (h.contains(ExportCsvService.WORD)) {
				headerStrListNormalized.add(ExportCsvService.WORD);
			} else if (h.contains(ExportCsvService.LEMMA)) {
				headerStrListNormalized.add(ExportCsvService.LEMMA);
			} else if (h.contains(ExportCsvService.TAG)) {
				headerStrListNormalized.add(ExportCsvService.TAG);
			}
		});
		if (header) {
			String[] headerStrs = ArrayUtils.addAll(
					headerStrListNormalized.toArray(new String[headerStrListNormalized.size()]),
					ExportCsvService.FREQUENCY,
					String.format("%s: %d - %s: %d", ExportCsvService.ELEMENT, queryResponse.getFrequency().getTotal(),
							ExportCsvService.OVERALL_FREQ, queryResponse.getFrequency().getTotalFreq()));
			strList.add(headerStrs);
		}
		for (FrequencyResultLine frl : queryResponse.getFrequency().getItems()) {
			String[] line = ArrayUtils.addAll(frl.getWord().toArray(new String[frl.getWord().size()]),
					String.valueOf(frl.getFreq()));
			strList.add(line);
		}
		this.appendCsv(strList, filePathStr);
	}

	private void elaborateTextualQueryRequestCsv(QueryResponse queryResponse, String filePathStr, boolean header)
			throws Exception {
		List<String[]> strList = new ArrayList<>();
		if (header) {
			String[] headerStrs = new String[] { ExportCsvService.LEFT, ExportCsvService.KWIC, ExportCsvService.RIGHT };
			strList.add(headerStrs);
		}
		for (KWICLine kwic : queryResponse.getKwicLines()) {
			String[] line = new String[] { kwic.getLeftContext().get(0), kwic.getKwic(),
					kwic.getRightContext().get(0) };
			strList.add(line);
		}
		this.appendCsv(strList, filePathStr);
	}

	private void elaborateWordListRequestCsv(QueryResponse queryResponse, String filePathStr, boolean header)
			throws Exception {
		List<String[]> strList = new ArrayList<>();
		if (header) {
			String[] headerStrs = new String[] { queryResponse.getWordList().getSearchAttribute(),
					ExportCsvService.FREQUENCY };
			strList.add(headerStrs);
		}
		for (WordListItem wli : queryResponse.getWordList().getItems()) {
			String[] line = new String[] { wli.getWord(), String.valueOf(wli.getFrequency()) };
			strList.add(line);
		}
		this.appendCsv(strList, filePathStr);
	}

	public void storageTmpFileCsvFromQueryResponse(QueryResponse queryResponse, QueryRequest.RequestType requestType,
			String filePathStr, boolean header) throws Exception {

		switch (requestType) {
		case COLLOCATION_REQUEST:
			this.elaborateCollocationCsv(queryResponse, filePathStr, header);
			break;
		case METADATA_FREQUENCY_QUERY_REQUEST:
			this.elaborateMetadataFrequencyCsv(queryResponse, filePathStr, header);
			break;
		case MULTI_FREQUENCY_QUERY_REQUEST:
			this.elaborateMultilevelFrequencyCsv(queryResponse, filePathStr, header);
			break;
		case TEXTUAL_QUERY_REQUEST:
			this.elaborateTextualQueryRequestCsv(queryResponse, filePathStr, header);
			break;
		case WORD_LIST_REQUEST:
			this.elaborateWordListRequestCsv(queryResponse, filePathStr, header);
			break;
		default:
			break;
		}
	}

	private void writeCsv(List<String[]> lines, String filePathStr) throws Exception {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePathStr))) {
			writer.writeAll(lines);
		}
	}

}
