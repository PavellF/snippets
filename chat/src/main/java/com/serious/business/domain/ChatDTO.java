package com.serious.business.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChatDTO {

	private Integer id;
	private String title;
	
	private ChatDTO() {}
	
}
