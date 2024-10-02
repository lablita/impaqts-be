/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.drwolf.impaqtsbe.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KWICLine {
	private Map<String, String> references = new HashMap<>();
	private String ref;
	private List<String> leftContext;
	private String kwic;
	private List<String> rightContext;
	private Long pos;
	private String startTime;
	private String videoUrl;
	private Long docNumber;

	public KWICLine(String ref, List<String> leftContext, String kwic, List<String> rightContext) {
		this.ref = ref;
		this.leftContext = leftContext;
		this.kwic = kwic;
		this.rightContext = rightContext;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		KWICLine kwicLine = (KWICLine) o;
		return this.getRef().equals(kwicLine.getRef()) && this.getLeftContext().equals(kwicLine.getLeftContext())
				&& this.getKwic().equals(kwicLine.getKwic()) && this.getRightContext()
				.equals(kwicLine.getRightContext());
	}

	public Long getDocNumber() {
		return docNumber;
	}

	public String getKwic() {
		return this.kwic;
	}

	public List<String> getLeftContext() {
		return this.leftContext;
	}

	public Long getPos() {
		return pos;
	}

	public String getRef() {
		return this.ref;
	}

	public Map<String, String> getReferences() {
		return references;
	}

	public List<String> getRightContext() {
		return this.rightContext;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getRef(), this.getLeftContext(), this.getKwic(), this.getRightContext());
	}

	public void setDocNumber(Long docNumber) {
		this.docNumber = docNumber;
	}

	public void setKwic(String kwic) {
		this.kwic = kwic;
	}

	public void setLeftContext(List<String> leftContext) {
		this.leftContext = leftContext;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setReferences(Map<String, String> references) {
		this.references = references;
	}

	public void setRightContext(List<String> rightContext) {
		this.rightContext = rightContext;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
