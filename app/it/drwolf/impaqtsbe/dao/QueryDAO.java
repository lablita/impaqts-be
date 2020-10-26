package it.drwolf.impaqtsbe.dao;

import com.sketchengine.manatee.Concordance;
import com.sketchengine.manatee.Corpus;
import com.sketchengine.manatee.KWICLines;
import it.drwolf.impaqtsbe.dto.KWICLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class QueryDAO {

	private final static Integer MAXLINE = 15;
	private static final Logger logger = LoggerFactory.getLogger(QueryDAO.class);

	public List<KWICLine> testSingleWord(String word) throws InterruptedException {
		Corpus corpus = new Corpus("susanne");
		Concordance concordance = new Concordance();
		concordance.load_from_query(corpus, word, 0, 0);
		int start = 0;
		int count = 0;
		int end = 0;
		List<KWICLine> kwicLines = new ArrayList<>();
		while (!concordance.finished() || end <= count) {
			Thread.sleep(1);
			count = concordance.size();
			Integer maxLine = QueryDAO.MAXLINE;
			if (maxLine > count) {
				maxLine = count;
			}
			end = start + maxLine;
			this.logger.warn(String.format("Size: %d\tStart: %d\tEnd: %d", concordance.size(), start, end));
			KWICLines kl = new KWICLines(corpus, concordance.RS(false, start, end), "15#", "15#", "word", "word", "s",
					"#", 100);
			for (int linenum = 0; linenum < maxLine; linenum++) {
				if (!kl.nextline()) {
					break;
				}
				KWICLine kwicLine = new KWICLine(kl);
				kwicLines.add(kwicLine);
			}
			start = end;
		}
		concordance.delete();
		corpus.delete();
		return kwicLines;
	}
}
