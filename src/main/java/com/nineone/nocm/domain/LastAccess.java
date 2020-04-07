package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LastAccess {
	private boolean isFocus;
	private int currentChannelId;
	private boolean isContentWrapper;

	@Builder
	public LastAccess(boolean isFocus, int currentChannelId, boolean isContentWrapper) {
		this.isFocus = isFocus;
		this.currentChannelId = currentChannelId;
		this.isContentWrapper = isContentWrapper;
	}
}
