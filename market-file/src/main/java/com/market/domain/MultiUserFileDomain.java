package com.market.domain;

public class MultiUserFileDomain extends UserFileDomain {

	private String chunks;
	private String chunk;
	public String getChunks() {
		return chunks;
	}
	public void setChunks(String chunks) {
		this.chunks = chunks;
	}

	public String getChunk() {
		return chunk;
	}
	public void setChunk(String chunk) {
		this.chunk = chunk;
	}
    
}
