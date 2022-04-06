package com.crypto.crypto.controller.base.vo;

import java.util.List;

public class JsonBaseVO<T> {
	private Long count;
	private List<T> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "{" +
				"count=" + count +
				", results=" + results +
				'}';
	}
}
