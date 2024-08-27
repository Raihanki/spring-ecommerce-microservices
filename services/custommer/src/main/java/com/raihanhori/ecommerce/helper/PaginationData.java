package com.raihanhori.ecommerce.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationData {

	private Integer total_page;
	
	private Integer current_page;
	
	private Integer total_data_per_page;
	
	private Integer total_data;
	
}
